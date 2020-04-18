package com.expense.tracker.service.impl;

import ch.qos.logback.classic.Logger;
import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.exceptions.WalletNotFoundException;
import com.expense.tracker.mappers.WalletDetailRowMapper;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

@Component
public class WalletServiceImpl implements IWalletService {

  @Autowired JdbcTemplate jdbcTemplate;
  Logger logger = (Logger) LoggerFactory.getLogger(WalletServiceImpl.class);

  @Override
  public ResponseEntity<Wallet> getWalletDetails(String emailId, String walletName)
      throws Exception { // , ExpenseTrackerException, UserNotFoundException{
    logger.debug("Fetching details of Wallet: " + walletName);
    System.out.println(emailId);
    SimpleJdbcCall simpleJdbcCall =
        new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("fetch_wallet_details_v1dot0")
            .returningResultSet("walletDetails", new WalletDetailRowMapper());

    SqlParameterSource inputParameter =
        new MapSqlParameterSource()
            .addValue("in_email_id", emailId.toLowerCase())
            .addValue("in_wallet_name", walletName);

    try {
      Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
      // System.out.println(out.get("walletDetails"));
      List<Wallet> wallet = (List<Wallet>) out.get("walletDetails");

      if (!wallet.isEmpty()) {
        return new ResponseEntity<>(wallet.get(0), null, HttpStatus.OK);
      }
    } catch (Exception e) {
      e.printStackTrace();
      logger.debug("SQL Exception while executing the Procedure : fetch_user_details_v1dot0" + e);
    }
    return null;
  }

  @Override
  public ResponseEntity<Wallet> createNewWallet(Wallet newWallet) throws ExpenseTrackerException {
    logger.debug("Creating new wallet with name: " + newWallet.getWalletName());
    try {
      SimpleJdbcCall simpleJdbcCall =
          new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_wallet_v1dot0");

      SqlParameterSource inputParameter =
          new MapSqlParameterSource()
              .addValue("in_wallet_name", newWallet.getWalletName())
              .addValue("in_amount", newWallet.getWalletName())
              .addValue("in_status", newWallet.getWalletName())
              .addValue("in_user_id", newWallet.getWalletName());

      Map<String, Object> out = simpleJdbcCall.execute(inputParameter);
      int responseCode = (int) out.get("response_code");
      if (responseCode == 0) {
        newWallet.setWalletId((Long) out.get("f_id"));
        newWallet.setWalletUsers((List<String>) out.get("wallet_users"));
      }
      if (responseCode == -1) {
        throw new ExpenseTrackerException(
            ((String) out.get("error_code")).concat(" ").concat((String) out.get("error_desc")));
      }
    } catch (Exception e) {
      logger.debug(
          "SQL Exception while inserting new user details into t_wallet_details table." + e);
      throw new ExpenseTrackerException(e.getMessage());
    }
    return new ResponseEntity<>(newWallet, null, HttpStatus.OK);
  }
}
