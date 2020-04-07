package com.expense.tracker.models;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class User {

	private Long userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String mobileNo;
	private Map<Long, String> userWallets;

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

	public Map<Long, String> getUserWallets() {
		return userWallets;
	}

	public void setUserWallets(Map<Long, String> userWallets) {
		this.userWallets = userWallets;
	}
}
