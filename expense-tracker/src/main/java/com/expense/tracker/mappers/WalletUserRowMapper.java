package com.expense.tracker.mappers;

import ch.qos.logback.classic.Logger;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class WalletUserRowMapper implements RowMapper<Map> {
	Logger logger = (Logger) LoggerFactory.getLogger(WalletUserRowMapper.class);
	Map<Long, String> walletUsers = new HashMap<Long, String>();
	@Override
	public Map mapRow(ResultSet resultSet, int i) {
		
		try {
			walletUsers.put(resultSet.getLong("f_id"), resultSet.getString("f_first_name"));

		} catch (Exception e) {
			logger.debug("SQL Exception while executing the Row Mapper" + e);
		}

		return walletUsers;
	}
}
