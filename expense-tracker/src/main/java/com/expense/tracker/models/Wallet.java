package com.expense.tracker.models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Wallet {
	private Long walletId;
	private String walletName;
	private Long amount;
	private Long createdBy;
	private Date createdTime;
	private String status;
	private List<Map> walletUsers;

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public String getWalletName() {
		return walletName;
	}

	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Map> getWalletUsers() {
		return walletUsers;
	}

	public void setWalletUsers(List<Map> walletUsers) {
		this.walletUsers = walletUsers;
	}
}
