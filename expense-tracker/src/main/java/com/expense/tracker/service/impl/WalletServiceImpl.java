package com.expense.tracker.service.impl;

import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.WalletNotFoundException;
import com.expense.tracker.mappers.WalletDetailRowMapper;
import com.expense.tracker.mappers.WalletUserRowMapper;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class WalletServiceImpl implements IWalletService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	Logger logger = (Logger) LoggerFactory.getLogger(WalletServiceImpl.class);

	@Override
	public Wallet getWalletDetails(Long walletId, String walletName) {
		logger.debug("Fetching details of Wallet: " + walletId);

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("fetch_wallet_details_v1dot0")
				.returningResultSet("walletDetails", new WalletDetailRowMapper())
				.returningResultSet("walletUsers", new WalletUserRowMapper());
		SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_wallet_id", walletId);

		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);

		List<Wallet> wallet = (List<Wallet>) out.get("walletDetails");
		List<Map> walletUsers = (List<Map>) out.get("walletUsers");

		if (wallet.isEmpty()) {
			throw new WalletNotFoundException(walletName);
		} else {
			wallet.get(0).setWalletUsers(walletUsers.get(0));
		}
		return wallet.get(0);
	}

	@Override
	public Wallet createNewWallet(Wallet newWallet) {

		logger.debug("Creating new wallet with name: " + newWallet.getWalletName());
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withProcedureName("insert_wallet_details_v1dot0");

			SqlParameterSource inputParameter = new MapSqlParameterSource()
					.addValue("in_wallet_name", newWallet.getWalletName()).addValue("in_amount", newWallet.getAmount())
					.addValue("in_status", newWallet.getStatus()).addValue("in_user_id", newWallet.getCreatedBy());

			Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
			int responseCode = (int) out.get("response_code");

			if (responseCode == 0) {
				newWallet.setWalletId((Long) out.get("out_walletid"));
				newWallet.setCreatedTime(new Date());
				Map<Long, String> walletUser = new HashMap<Long, String>();
				walletUser.put((Long) out.get("out_walletid"), (String) out.get("out_first_name"));

				newWallet.setWalletUsers(walletUser);

			}
			if (responseCode == -1) {
				if ("NULL_VALUE".equals((String) out.get("error_code"))) {
					throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.NULL_VALUE);
				} else if ("DUPLICATE_ENTRY".equals((String) out.get("error_code"))) {
					throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.DUPLICATE_KEY);
				} else {
					throw new ExpenseTrackerException(
							((String) out.get("error_code")).concat(" ").concat((String) out.get("error_desc")),
							ErrorCode.TECHNICAL_ERROR);
				}

			}
		} catch (Exception e) {
			logger.debug("SQL Exception while inserting new user details into t_wallet_details table." + e);
			throw new ExpenseTrackerException("Technical Error! Please try again later.", ErrorCode.TECHNICAL_ERROR);
		}
		return newWallet;
	}

	public Wallet addUser(Wallet wallet, String emailId) {
		logger.debug("Adding user " + emailId + " to Wallet: " + wallet.getWalletId());

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("insert_wallet_user_v1dot0");

		SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_wallet_id", wallet.getWalletId())
				.addValue("in_email_id", emailId);

		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
		int responseCode = (int) out.get("response_code");
		if (responseCode == 0) {
			Map walletUsers = wallet.getWalletUsers();
			walletUsers.put((Long) out.get("out_userid"), out.get("out_first_name"));
		}
		if (responseCode == -1) {
			if ("USER_NOT_FOUND".equals((String) out.get("error_code"))) {
				throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.NO_SUCH_USER);
			}
			if ("NULL_VALUE".equals((String) out.get("error_code"))) {
				throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.NULL_VALUE);
			} else if ("DUPLICATE_ENTRY".equals((String) out.get("error_code"))) {
				throw new ExpenseTrackerException((String) out.get("error_desc"), ErrorCode.DUPLICATE_KEY);
			} else {
				throw new ExpenseTrackerException(
						((String) out.get("error_code")).concat(" ").concat((String) out.get("error_desc")),
						ErrorCode.TECHNICAL_ERROR);
			}
		}

		return wallet;
	}
}
