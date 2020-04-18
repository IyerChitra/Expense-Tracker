package com.expense.tracker.mappers;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.Wallet;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserWalletRowMapper implements RowMapper<Wallet> {

  @Override
  public Wallet mapRow(ResultSet resultSet, int i) {
    Wallet wallet = new Wallet();

    try {
      wallet.setWalletId(resultSet.getLong("f_wallet_Id"));
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      try { // doubt - why is this asking to surround this with try catch
        throw new ExpenseTrackerException(e.getMessage());
      } catch (ExpenseTrackerException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      e.printStackTrace();
    }
    System.out.println(wallet);
    return wallet;
  }
}
