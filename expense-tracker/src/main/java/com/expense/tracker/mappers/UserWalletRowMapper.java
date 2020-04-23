package com.expense.tracker.mappers;

import ch.qos.logback.classic.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserWalletRowMapper implements RowMapper<Map> {
	Logger logger = (Logger) LoggerFactory.getLogger(UserWalletRowMapper.class);
	Map<Long, String> userWallets = new HashMap<Long, String>();

	@Override
	public Map mapRow(ResultSet resultSet, int i) {

		try {
			userWallets.put(resultSet.getLong("f_id"), resultSet.getString("f_wallet_name"));
		} catch (SQLException e) {
			logger.debug("SQL Exception while executing the Row Mapper" + e);
		}
		return userWallets;
	}
}
