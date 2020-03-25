package com.expense.tracker.service.impl;

import com.expense.tracker.models.User;
import com.expense.tracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceImpl implements IUserService {

  @Autowired JdbcTemplate jdbcTemplate;

  @Override
  public User getUserDetails(Long userId) {

    SimpleJdbcCall simpleJdbcCall =
        new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("fetch_user_details_v1dot0")
            .returningResultSet(
                "userDetails",
                new RowMapper<User>() {
                  @Override
                  public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setUserId(resultSet.getLong("f_id"));
                    user.setFirstName(resultSet.getString("f_first_name"));
                    user.setLastName(resultSet.getString("f_last_name"));
                    user.setEmailId(resultSet.getString("f_email"));
                    user.setMobileNo(resultSet.getString("f_mobile_number"));
                    return user;
                  }
                });

    SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_user_id", userId);
    Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
    List<User> users = (List<User>) out.get("userDetails");
    return users.get(0);
  }
}
