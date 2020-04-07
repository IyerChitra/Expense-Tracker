package com.expense.tracker.models;

import java.util.Date;
import java.util.List;

public class Wallet {
	private Long walletId;
    private String walletName;
    private Long amount;
    private String createdBy;
    private Date createdTime;
    private String status;
    private List<String> walletUsers; // added this member as each wallet should show the list of users accessing that wallet
    
	
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
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
	public List<String> getWalletUsers() {
		return walletUsers;
	}
	public void setWalletUsers(List<String> walletUsers) {
		this.walletUsers = walletUsers;
	}    

}
