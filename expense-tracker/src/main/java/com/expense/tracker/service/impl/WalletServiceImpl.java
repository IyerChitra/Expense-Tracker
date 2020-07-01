package com.expense.tracker.service.impl;

import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.WalletNotFoundException;
import com.expense.tracker.mappers.WalletDetailRowMapper;
import com.expense.tracker.mappers.WalletUserRowMapper;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Component;

@Component
public class WalletServiceImpl implements IWalletService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

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

	public Long addUser(List<User> users, Long walletId) {
		logger.debug("Adding Users to Wallet: " + walletId);

		StringBuffer sb = new StringBuffer();
		Iterator it = users.listIterator();
		while (it.hasNext()) {
			User user = (User) it.next();
			sb.append(user.getUserId()).append(","); // use streams
		}

		String userIds = sb.substring(0, sb.length() - 1).toString();
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("insert_bulk_user_wallet_ref_v1dot0"); // modify the sp to increment the index, also transaction
		SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_wallet_id", walletId)
				.addValue("user_ids", userIds);

		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);

		return walletId;
	}
}
