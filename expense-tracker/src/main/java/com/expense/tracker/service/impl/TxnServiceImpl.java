package com.expense.tracker.service.impl;

import ch.qos.logback.classic.Logger;
import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.constants.TxnStatus;
import com.expense.tracker.constants.TxnType;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.TransactionInvalidException;
import com.expense.tracker.exceptions.TxnNotFoundException;
import com.expense.tracker.mappers.TxnDetailRowMapper;
import com.expense.tracker.mappers.UserDetailRowMapper;
import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.Transaction;
import com.expense.tracker.service.ITxnService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class TxnServiceImpl implements ITxnService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(TxnServiceImpl.class);

	@Override
	public Transaction getTxnDetails(Long txnId) {
		logger.debug("Getting Transaction Details for txnId: {}", txnId);
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("fetch_txn_details_v1dot0")
				.returningResultSet("txnDetails", new TxnDetailRowMapper());

		SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", txnId);
		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
		List<Transaction> txns = (List<Transaction>) out.get("txnDetails");

		if (!txns.isEmpty()) {
			return txns.get(0);
		} else {
			throw new TxnNotFoundException(txnId.toString());
		}
	}

	@Override
	public Transaction txnCredit(Transaction txn) {
		// CHECK FOR VALIDATIONS AND RAISE ERRORS
		logger.debug("Recording Credit Transaction by: {},{}", txn.getUser(), txn.getWallet());
		SimpleJdbcCall simpleJdbcCall = null;
		try {
			Long txnId = createTxnId();
			logger.debug("Inserting Transaction with ID: {}", txnId);
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("t_txn_master")
					.usingGeneratedKeyColumns("f_id");
			SqlParameterSource insertParameter = new MapSqlParameterSource().addValue("f_txn_id", txnId)
					.addValue("f_wallet_id", txn.getWallet().getWalletId()).addValue("f_txn_amount", txn.getTxnAmount())
					.addValue("f_comments", txn.getComments()).addValue("f_user_id", txn.getUser().getUserId())
					.addValue("f_txn_type", txn.getTxnType()).addValue("f_txn_status", TxnStaus.INITIATED);
			simpleJdbcInsert.execute(insertParameter);

			// FIXME: replace Wallet Object with just wallet Id
			if (txn.getTxnType() == TxnType.CREDIT
					&& txn.getWallet().getWalletUsers().containsKey(txn.getUser().getUserId())) {

				simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("insert_credit_txn_v1dot0");

				SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
						.addValue("in_walletid", txn.getWallet().getWalletId())
						.addValue("in_amount", txn.getTxnAmount()).addValue("in_error_code", "")
						.addValue("in_wallet_amount", txn.getWallet().getAmount());

				Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
				int responseCode = (int) out.get("response_code");
				if (responseCode == 0) {
					txn.setTxnid((Long) out.get("out_txn_id"));
					txn.setStatus(TxnStatus.SUCCESS);
				}
				if (responseCode == -1) {
					if ("NULL_VALUE".equals((String) out.get("error_code"))) {
						throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.NULL_VALUE);
					} else if ("DUPLICATE_ENTRY".equals((String) out.get("error_code"))) {
						throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.DUPLICATE_KEY);
					} else {
						txn.setTxnid((Long) out.get("out_txn_id"));
						txn.setStatus(TxnStatus.FAILED);
						SqlParameterSource errorInputParameter = new MapSqlParameterSource()
								.addValue("in_txn_id", createTxnId())
								.addValue("in_walletid", txn.getWallet().getWalletId())
								.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
								.addValue("in_userid", txn.getUser().getUserId())
								.addValue("in_error_code", "TECHNICAL_ERROR")
								.addValue("in_wallet_amount", txn.getWallet().getAmount());
						simpleJdbcCall.execute(errorInputParameter);
						throw new ExpenseTrackerException(
								((String) out.get("error_code")).concat(" ").concat((String) out.get("error_desc")),
								ErrorCode.TECHNICAL_ERROR);
					}

				}
			}
			else {
				txn.setTxnid(txnId);
				txn.setStatus(TxnStatus.FAILED);
				simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("insert_credit_txn_v1dot0");
				if (txn.getTxnType() != TxnType.CREDIT) {
					SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
							.addValue("in_walletid", txn.getWallet().getWalletId())
							.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
							.addValue("in_userid", txn.getUser().getUserId())
							.addValue("in_error_code", "INVALID_TRANSACTION")
							.addValue("in_wallet_amount", txn.getWallet().getAmount());
					Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
					throw new TransactionInvalidException(txn.getTxnType().toString());
				} else {
					SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
							.addValue("in_walletid", txn.getWallet().getWalletId())
							.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
							.addValue("in_userid", txn.getUser().getUserId())
							.addValue("in_error_code", "UNAUTHORIZED_TXN")
							.addValue("in_wallet_amount", txn.getWallet().getAmount());
					Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
					throw new TransactionInvalidException(txn.getWallet().getWalletName());
				}

			}

		} catch (Exception e) {
			logger.debug("SQL Exception while inserting txn details into t_txn_master table." + e);
			SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
					.addValue("in_walletid", txn.getWallet().getWalletId()).addValue("in_amount", txn.getTxnAmount())
					.addValue("in_comments", txn.getComments()).addValue("in_userid", txn.getUser().getUserId())
					.addValue("in_error_code", "TECHNICAL_ERROR")
					.addValue("in_wallet_amount", txn.getWallet().getAmount());
			Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
			throw new ExpenseTrackerException("Transaction Unsuccessful! Please try again", ErrorCode.TECHNICAL_ERROR);
		}
		return txn;
	}

	@Override
	public Transaction txnDebit(Transaction txn) {
		// TODO Auto-generated method stub
		logger.debug("Recording Debit Transaction by: ".concat(txn.getUser().toString()).concat(" to wallet: ")
				.concat(txn.getWallet().getWalletId().toString()));
		try {
			Long txnId = createTxnId();
			logger.debug("Inserting Transaction with ID: ".concat(txnId.toString()));
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("t_txn_master")
					.usingGeneratedKeyColumns("f_id");
			SqlParameterSource insertParameter = new MapSqlParameterSource().addValue("f_txn_id", txnId)
					.addValue("f_wallet_id", txn.getWallet().getWalletId()).addValue("f_txn_amount", txn.getTxnAmount())
					.addValue("f_comments", txn.getComments()).addValue("f_user_id", txn.getUser().getUserId())
					.addValue("f_txn_type", txn.getTxnType()).addValue("f_txn_status", "INITIATED");
			simpleJdbcInsert.execute(insertParameter);

			if (txn.getTxnType() == TxnType.DEBIT
					&& txn.getWallet().getWalletUsers().containsKey(txn.getUser().getUserId())
					&& txn.getTxnAmount() < txn.getWallet().getAmount()) {
				SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
						.withProcedureName("insert_debit_txn_v1dot0");

				SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
						.addValue("in_walletid", txn.getWallet().getWalletId())
						.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
						.addValue("in_userid", txn.getUser().getUserId()).addValue("f_txn_type", txn.getTxnType())
						.addValue("f_error_code", "");

				Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
				int responseCode = (int) out.get("response_code");

				if (responseCode == 0) {
					txn.setTxnid((Long) out.get("out_txn_id"));
					txn.setStatus(TxnStatus.SUCCESS);
				}
				else (responseCode == -1) {
					if ("NULL_VALUE".equals((String) out.get("error_code"))) {
						throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.NULL_VALUE);
					} else if ("DUPLICATE_ENTRY".equals((String) out.get("error_code"))) {
						throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.DUPLICATE_KEY);
					} else if ("INSUFFICIENT_BALANCE".equals((String) out.get("error_code"))) {
						throw new ExpenseTrackerException((String) out.get("error_desc"),
								ErrorCode.INSUFFICIENT_BALANCE);
					} else {
						txn.setTxnid((Long) out.get("out_txn_id"));
						txn.setStatus(TxnStatus.FAILED);
						throw new ExpenseTrackerException(
								((String) out.get("error_code")).concat(" ").concat((String) out.get("error_desc")),
								ErrorCode.TECHNICAL_ERROR);
					}

				}
			} else {
				txn.setTxnid(txnId);
				txn.setStatus(TxnStatus.FAILED);
				SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
						.withProcedureName("insert_credit_txn_v1dot0");
				if (txn.getTxnType() != TxnType.DEBIT) {
					SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
							.addValue("in_walletid", txn.getWallet().getWalletId())
							.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
							.addValue("in_userid", txn.getUser().getUserId())
							.addValue("f_error_code", "INVALID_TRANSACTION");
					Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
					throw new TransactionInvalidException(txn.getTxnType().toString());
				} else if (txn.getWallet().getWalletUsers().containsKey(txn.getUser().getUserId())) {
					SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
							.addValue("in_walletid", txn.getWallet().getWalletId())
							.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
							.addValue("in_userid", txn.getUser().getUserId())
							.addValue("f_error_code", "UNAUTHORIZED_TXN");
					Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
					throw new TransactionInvalidException(txn.getWallet().getWalletName());
				} else {
					SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_txn_id", createTxnId())
							.addValue("in_walletid", txn.getWallet().getWalletId())
							.addValue("in_amount", txn.getTxnAmount()).addValue("in_comments", txn.getComments())
							.addValue("in_userid", txn.getUser().getUserId())
							.addValue("f_error_code", "INSUFFICIENT_BALANCE");
					Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
					throw new TransactionInvalidException(txn.getWallet().getAmount());
				}
			}
		} catch (Exception e) {
			logger.debug("SQL Exception while inserting txn details into t_txn_master table." + e);
			throw new ExpenseTrackerException("Transaction Unsuccessful! Please try again", ErrorCode.TECHNICAL_ERROR);
		}
		return txn;
	}

	//FIXME: harden this method
	private synchronized static long createTxnId() {
		// use hashing
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime = ft.format(dNow);
		return Long.parseUnsignedLong(datetime);
	}
}
