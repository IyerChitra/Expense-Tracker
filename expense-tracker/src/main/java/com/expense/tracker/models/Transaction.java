package com.expense.tracker.models;

import java.util.Date;

public class Transaction {

	private Long txnid; //Long
	private Long walletId;
	private User user; // use user Object
	private enum txntype{
		CREDIT,
		DEBIT
	};
	private enum txnStatus{
		SUCCESS,
		FAILED,
		IN_PROGRESS
	}
	private txntype txnType;
	private txnStatus status;
	public txnStatus getStatus() {
		return status;
	}

	public void setStatus(txnStatus status) {
		this.status = status;
	}

	private Long txnAmount;
	private Date txntime;
	private String comments;
	//comments string
	//add txn status as enum

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setTxnAmount(Long txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Long getTxnid() {
		return txnid;
	}

	public void setTxnid(Long txnid) {
		this.txnid = txnid;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	

	public txntype getTxntype() {
		return txnType;
	}

	public void setTxntype(txntype txnType) {
		this.txnType = txnType;
	}

	public Date getTxntime() {
		return txntime;
	}

	public void setTxntime(Date txntime) {
		this.txntime = txntime;
	}
}
