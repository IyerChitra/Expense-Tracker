package com.expense.tracker.models;

import java.util.Date;

public class Transaction {

  private String txnid;
  private String walletId;
  private String userName;
  private String txntype;
  private int txnAmount;
  private Date txntime;

  public String getTxnid() {
    return txnid;
  }

  public void setTxnid(String txnid) {
    this.txnid = txnid;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTxntype() {
    return txntype;
  }

  public void setTxntype(String txntype) {
    this.txntype = txntype;
  }

  public int getTxnAmount() {
    return txnAmount;
  }

  public void setTxnAmount(int txnAmount) {
    this.txnAmount = txnAmount;
  }

  public Date getTxntime() {
    return txntime;
  }

  public void setTxntime(Date txntime) {
    this.txntime = txntime;
  }
}
