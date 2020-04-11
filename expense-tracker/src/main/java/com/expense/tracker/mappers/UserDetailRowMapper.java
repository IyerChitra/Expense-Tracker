package com.expense.tracker.mappers;

import com.expense.tracker.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDetailRowMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet resultSet, int i) {
    User user = new User();
    try {
      user.setUserId(resultSet.getLong("f_id"));
      user.setFirstName(resultSet.getString("f_first_name"));
      user.setLastName(resultSet.getString("f_last_name"));
      user.setEmailId(resultSet.getString("f_email"));
      user.setMobileNo(resultSet.getString("f_mobile_number"));
    } catch (SQLException e) {

    }
    return user;
  }
}
