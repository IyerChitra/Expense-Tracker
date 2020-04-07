package com.expense.tracker.service.impl;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.UserNotFoundException;
import com.expense.tracker.mappers.UserDetailRowMapper;
import com.expense.tracker.mappers.UserWalletRowMapper;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IUserService;

import ch.qos.logback.classic.Logger;

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
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UserServiceImpl implements IUserService {

  @Autowired JdbcTemplate jdbcTemplate;
  Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public ResponseEntity<User> getUserDetails(String emailId) throws Exception{//, ExpenseTrackerException, UserNotFoundException{
	  System.out.println("Inside UserServiceImpl class: getUserDetails method");
	  System.out.println(emailId);
    SimpleJdbcCall simpleJdbcCall =
        new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("fetch_user_details_v1dot0")
            .returningResultSet(
                "userDetails", new UserDetailRowMapper())
            .returningResultSet("walletId", new UserWalletRowMapper());

    SqlParameterSource inputParameter = new MapSqlParameterSource().addValue("in_user_id", emailId.toLowerCase());
    
    
    	try {
		Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
		System.out.println(out.get("userDetails"));
		List<User> users =  (List<User>) out.get("userDetails");
		
		if(!users.isEmpty())
	    {
			List<Wallet> wallets = (List<Wallet>) out.get("walletId");
			System.out.println("Wallets: ");
			System.out.println(wallets.toString());
			User user = users.get(0);
			user.setWalletId(wallets);
	    	//List<User> users = (List<User>) out.get("userDetails");
	    	return new ResponseEntity<>(user, null, HttpStatus.OK);
	    }
	    else
	    {
	    	throw new UserNotFoundException(emailId);
	    }
		
		//user = out.get("userDetails").;
	} catch (Exception e) {
		e.printStackTrace();
		logger.debug("SQL Exception while executing the Procedure : fetch_user_details_v1dot0" + e);
		throw new ExpenseTrackerException(e.getMessage());
		
	}
    
  }
  
  
  @Override
  public ResponseEntity<User> createNewUser(User newUser) throws ExpenseTrackerException{
	  /*Using JDBC Insert*/
	  logger.debug("Trying to create New User with emailID: ".concat(newUser.getEmailId()));
	  try{
	  SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
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
	  }
	  catch(Exception e){
		  
		  if(e instanceof DuplicateKeyException)
		  {
			  logger.debug(e.getMessage());
			  throw new ExpenseTrackerException("EmailID or Mobile number already registered. please register with another Email ID!");
		  }
		  else if (e instanceof DataIntegrityViolationException)
		  {
			  logger.debug(e.getMessage());
			  throw new ExpenseTrackerException("Cannot take Empty Values. Please enter all fields!");
		  }
		  else{
		  logger.debug("SQL Exception while inserting new user details into t_user_details table." + e);
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
