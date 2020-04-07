package com.expense.tracker.mappers;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.impl.WalletServiceImpl;

import ch.qos.logback.classic.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class WalletDetailRowMapper implements RowMapper<Wallet> {
	Logger logger = (Logger) LoggerFactory.getLogger(WalletDetailRowMapper.class);
  @Override
  public Wallet mapRow(ResultSet resultSet, int i) {
    Wallet wallet = new Wallet();
    
    try{  
    	  wallet.setWalletId(resultSet.getLong("f_id")); 
    	  wallet.setWalletName(resultSet.getString("f_wallet_name"));
    	  wallet.setAmount(resultSet.getLong("f_amount"));
    	  wallet.setStatus(resultSet.getString("f_wallet_status"));
    	  wallet.setCreatedBy("gorti");
    	  wallet.setCreatedTime(resultSet.getDate("f_created_time"));
    }
    catch(Exception e){
			logger.debug("SQL Exception while executing the Row Mapper" + e);
		
	}
      
    return wallet;
  }
}
