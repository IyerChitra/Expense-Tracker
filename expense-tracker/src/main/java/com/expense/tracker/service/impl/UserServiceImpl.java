package com.expense.tracker.service.impl;

import ch.qos.logback.classic.Logger;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.mappers.UserDetailRowMapper;
import com.expense.tracker.models.BaseResponse;
import com.expense.tracker.models.User;
import com.expense.tracker.service.IUserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<User> getUserDetails(String userId) {

    SimpleJdbcCall simpleJdbcCall =
        new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("fetch_user_details_v1dot0")
            .returningResultSet("userDetails", new UserDetailRowMapper());

    SqlParameterSource inputParameter =
        new MapSqlParameterSource().addValue("in_user_id", userId.toLowerCase());

    User user = null;
    try {
      Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
      user = (User) out.get("userDetails");
    } catch (Exception e) {
      logger.debug("SQL Exception while executing the Procedure : fetch_user_details_v1dot0" + e);
      throw new ExpenseTrackerException();
    }
    if (Objects.nonNull(user)) {
      // List<User> users = (List<User>) out.get("userDetails");
      return new ResponseEntity<>(user, null, HttpStatus.OK);
    } else {
      throw new UserNotFoundException();
    }
  }

  @Override
  public BaseResponse<User> createNewUser(User newUser) {
    /*Using JDBC Insert*/
    try {
      SimpleJdbcInsert simpleJdbcInsert =
          new SimpleJdbcInsert(jdbcTemplate)
              .withTableName("t_user_details")
              .usingGeneratedKeyColumns("f_id");
      Map<String, Object> inputParameters = new HashMap<String, Object>(2);
      inputParameters.put("f_first_name", newUser.getFirstName());
      inputParameters.put("f_last_name", newUser.getLastName());
      inputParameters.put("f_email", newUser.getEmailId());
      inputParameters.put("f_mobile_number", newUser.getMobileNo());

      Number newUserId = simpleJdbcInsert.executeAndReturnKey(inputParameters);
      newUser.setUserId(newUserId.longValue());
    } catch (Exception e) {
      logger.debug("SQL Exception while inserting new user details into t_user_details table." + e);
      return new BaseResponse<>(null, "SQL_EXCEPTION", "Back-end Error! Please retry.");
    }

    /*Using JDBC Insert*/

    /*Using JDBC Call*/
    SimpleJdbcCall simpleJdbcCall =
        new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("insert_user_details_v1dot0")
            .withReturnValue();
    SqlParameterSource inputParameter =
        new MapSqlParameterSource()
            .addValue("in_first_name", newUser.getFirstName())
            .addValue("in_last_name", newUser.getLastName())
            .addValue("in_email", newUser.getEmailId())
            .addValue("in_mobile_number", newUser.getMobileNo());
    Map out = simpleJdbcCall.execute(inputParameter);
    Long userId = (Long) out.get("f_id");
    newUser.setUserId(userId);
    /*Using JDBC Call*/

    return new BaseResponse<>(newUser, null, null);
  }
}
