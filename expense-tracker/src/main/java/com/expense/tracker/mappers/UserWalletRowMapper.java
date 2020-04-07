package com.expense.tracker.mappers;

import com.expense.tracker.models.Wallet;

import ch.qos.logback.classic.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserWalletRowMapper implements RowMapper<Wallet> {
	Logger logger = (Logger) LoggerFactory.getLogger(UserWalletRowMapper.class);

	@Override
	public Wallet mapRow(ResultSet resultSet, int i) {
		Wallet wallet = new Wallet();

		try {
			wallet.setWalletId(resultSet.getLong("f_wallet_Id"));
			wallet.setWalletName(resultSet.getString("f_wallet_name"));
		} catch (SQLException e) {
			logger.debug("SQL Exception while executing the Row Mapper" + e);
		}
		return wallet;
	}
}
