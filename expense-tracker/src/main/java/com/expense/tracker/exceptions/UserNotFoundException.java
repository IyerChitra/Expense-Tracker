package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;

public class UserNotFoundException extends ExpenseTrackerException {

	public UserNotFoundException(String var1) {
		super(String.format("User ID '%s' not found! Please Register as new User.", var1), ErrorCode.USER_NOT_FOUND);
	}
}
