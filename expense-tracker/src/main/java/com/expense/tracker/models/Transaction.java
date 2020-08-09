package com.expense.tracker.models;

import com.expense.tracker.constants.TxnStatus;
import com.expense.tracker.constants.TxnType;

// FIXME: override toString() for all objects
public class Transaction {

	private Long txnid; // Long
	private Wallet wallet;
	private User user; // use user Object
	private TxnType txnType;
	private TxnStatus status;

	private String errorCode;

	private Long txnAmount;
	private Long txntime;// ePOCH timestamp
	private String comments;
	// comments string
	// add txn status as enum

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

	public Long getTxnAmount() {
		return txnAmount;
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

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public TxnStatus getStatus() {
		return status;
	}

	public void setStatus(TxnStatus status) {
		this.status = status;
	}

	public TxnType getTxnType() {
		return txnType;
	}

	public void setTxnType(TxnType txnType) {
		this.txnType = txnType;
	}

	public Long getTxntime() {
		return txntime;
	}

	public void setTxntime(Long txntime) {
		this.txntime = txntime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
