package com.expense.tracker.mappers;

import ch.qos.logback.classic.Logger;
import com.expense.tracker.constants.TxnStatus;
import com.expense.tracker.constants.TxnType;
import com.expense.tracker.models.Transaction;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TxnDetailRowMapper implements RowMapper<User> {
	Logger logger = (Logger) LoggerFactory.getLogger(TxnDetailRowMapper.class);

	@Override
	public User mapRow(ResultSet resultSet, int i) {
		Transaction txn = new Transaction();
		Wallet wallet = new Wallet();
		User user = new User();
		try {
			txn.setTxnid(resultSet.getLong("f_txn_id"));
			wallet.setWalletId(resultSet.getLong("f_wallet_id"));
			txn.setWallet(wallet);
			user.setUserId(resultSet.getLong("f_user_id"));
			txn.setUser(user);
			txn.setTxntype(TxnType.valueOf(resultSet.getString("f_txn_type")));
			txn.setStatus(TxnStatus.valueOf(resultSet.getString("f_txn_status")));
			txn.setComments(resultSet.getString("f_comments"));
			txn.setTxnAmount(resultSet.getLong("f_txn_amount"));
			txn.setErrorCode(resultSet.getString("f_error_code"));
			txn.setTxntime(resultSet.getLong("f_updated_time"));
		} catch (SQLException e) {
			logger.debug("SQL Exception while executing the Row Mapper" + e);
		}

		return user;
	}
}
