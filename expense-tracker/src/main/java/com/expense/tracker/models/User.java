package com.expense.tracker.models;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class User {

  private Long userId;
  private String firstName;
  private String lastName;
  private String emailId;
  private String mobileNo;
  private List<Wallet> walletId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailId() {
    return emailId;
  }

  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public List<Wallet> getWalletId() {
    return walletId;
  }

  public void setWalletId(List<Wallet> walletId) {
    this.walletId = walletId;
  }
}
