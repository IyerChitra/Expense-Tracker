package com.expense.tracker.service.impl;

import ch.qos.logback.classic.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.UserNotFoundException;
import com.expense.tracker.mappers.UserDetailRowMapper;
import com.expense.tracker.mappers.UserWalletRowMapper;
import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IUserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements IUserService {

	@Autowired
	JdbcTemplate jdbcTemplate;
	Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User getUserDetails(Long userId) {
		logger.debug("Getting user dteails for userId: " + userId);
		User user = null;
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("fetch_user_details_v1dot0")
				.returningResultSet("userDetails", new UserDetailRowMapper())
				.returningResultSet("userWallets", new UserWalletRowMapper());

		SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_user_id", userId);

		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);

		List<User> users = (List<User>) out.get("userDetails");

		if (!users.isEmpty()) {
			List<Map> wallets = (List<Map>) out.get("userWallets");

			user = users.get(0);
			if (!wallets.isEmpty())
				user.setUserWallets((Map<Long, String>) wallets.get(0));
		} else {
			throw new UserNotFoundException(userId);
		}

		return user;
	}

	@Override
	public User createNewUser(User newUser) {
		logger.debug("Trying to create New User with emailID: ".concat(newUser.getEmailId()));
		try {
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("t_user_details")
					.usingGeneratedKeyColumns("f_id");

			SqlParameterSource inputParameters = new MapSqlParameterSource()
					.addValue("f_first_name", newUser.getFirstName()).addValue("f_last_name", newUser.getLastName())
					.addValue("f_email", newUser.getEmailId()).addValue("f_mobile_number", newUser.getMobileNo());

			Number newUserId = simpleJdbcInsert.executeAndReturnKey(inputParameters);

			newUser.setUserId(newUserId.longValue());
		} catch (Exception e) {

			if (e instanceof DuplicateKeyException) {
				logger.debug(e.getMessage());
				throw new ExpenseTrackerException(
						"EmailID or Mobile number already registered. please register with another Email ID!",
						ErrorCode.DUPLICATE_USER);
			} else if (e instanceof DataIntegrityViolationException) {
				logger.debug(e.getMessage());
				throw new ExpenseTrackerException("Cannot take Empty Values. Please enter all fields!",
						ErrorCode.NULL_VALUE);
			} else {
				logger.debug("SQL Exception while inserting new user details into t_user_details table." + e);
				throw new ExpenseTrackerException("Technical Error! Please try again later.",
						ErrorCode.TECHNICAL_ERROR);
			}
		}

		return newUser;
	}

	@Override
	public List<User> getUserList(String emailId, Long walletId, Pagination page) {
		logger.debug("Trying to fetch users with emailID: ".concat(emailId));

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("fetch_user_email_v1dot0")
				.returningResultSet("userDetails", new UserDetailRowMapper());
		SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_email_Id", emailId)
				.addValue("in_wallet_id", walletId).addValue("in_offset", page.getOffset()).addValue("in_limit", page.getLimit());

		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
		List<User> users = (List<User>) out.get("userDetails");
		if(users.isEmpty()){
			throw new ExpenseTrackerException("No users found with the given email. Please try some other user", ErrorCode.NO_SUCH_USER);
		}
		return users;
	}

}
