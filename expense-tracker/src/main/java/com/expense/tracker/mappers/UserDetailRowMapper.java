package com.expense.tracker.mappers;

import com.expense.tracker.models.User;

import ch.qos.logback.classic.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDetailRowMapper implements RowMapper<User> {
	Logger logger = (Logger) LoggerFactory.getLogger(UserDetailRowMapper.class);

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
			logger.debug("SQL Exception while executing the Row Mapper" + e);
		}

		return user;
	}
}
