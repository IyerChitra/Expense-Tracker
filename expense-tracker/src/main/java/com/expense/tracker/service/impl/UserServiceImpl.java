package com.expense.tracker.service.impl;

import ch.qos.logback.classic.Logger;
import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.UserNotFoundException;
import com.expense.tracker.mappers.UserDetailRowMapper;
import com.expense.tracker.mappers.UserWalletRowMapper;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IUserService;
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

  @Autowired JdbcTemplate jdbcTemplate;
  Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public User getUserDetails(String emailId) {

    User user = null;
    SimpleJdbcCall simpleJdbcCall =
        new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("fetch_user_details_v1dot0")
            .returningResultSet("userDetails", new UserDetailRowMapper())
            .returningResultSet("walletId", new UserWalletRowMapper());

    SqlParameterSource inputParameter =
        new MapSqlParameterSource().addValue("in_user_id", emailId.toLowerCase());

    Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
    List<User> users = (List<User>) out.get("userDetails");
    if (!users.isEmpty()) {
      List<Wallet> wallets = (List<Wallet>) out.get("walletId");
      user = users.get(0);
      user.setWalletId(wallets);
    } else {
      throw new UserNotFoundException(emailId);
    }

    return user;
  }

  @Override
  public ResponseEntity<User> createNewUser(User newUser) throws ExpenseTrackerException {
    /*Using JDBC Insert*/
    logger.debug("Trying to create New User with emailID: ".concat(newUser.getEmailId()));
    try {
      SimpleJdbcInsert simpleJdbcInsert =
          new SimpleJdbcInsert(jdbcTemplate)
              .withTableName("t_user_details")
              .usingGeneratedKeyColumns("f_id");

      SqlParameterSource inputParameters =
          new MapSqlParameterSource()
              .addValue("f_first_name", newUser.getFirstName())
              .addValue("f_last_name", newUser.getLastName())
              .addValue("f_email", newUser.getEmailId())
              .addValue("f_mobile_number", newUser.getMobileNo());

      Number newUserId = simpleJdbcInsert.executeAndReturnKey(inputParameters);
      System.out.println(newUserId);
      newUser.setUserId(newUserId.longValue());
    } catch (Exception e) {

      if (e instanceof DuplicateKeyException) {
        logger.debug(e.getMessage());
        throw new ExpenseTrackerException(
            "EmailID or Mobile number already registered. please register with another Email ID!");
      } else if (e instanceof DataIntegrityViolationException) {
        logger.debug(e.getMessage());
        throw new ExpenseTrackerException("Cannot take Empty Values. Please enter all fields!");
      } else {
        logger.debug(
            "SQL Exception while inserting new user details into t_user_details table." + e);
        throw new ExpenseTrackerException("Technical Error! Please try again later.");
      }
    }

    /*Using JDBC Insert*/

    /*Using JDBC Call*/
    /* SimpleJdbcCall simpleJdbcCall =
           new SimpleJdbcCall(jdbcTemplate)
               .withProcedureName("insert_user_details_v1dot0")
               .withReturnValue();
    SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_first_name", newUser.getFirstName())
      														 .addValue("in_last_name", newUser.getLastName())
      														 .addValue("in_email", newUser.getEmailId()).addValue("in_mobile_number", newUser.getMobileNo());
    Map out = simpleJdbcCall.execute(inputParameter);
    Long userId = (Long) out.get("f_id");
    newUser.setUserId(userId);*/
    /*Using JDBC Call*/

    return new ResponseEntity<>(newUser, null, HttpStatus.OK);
  }
}
