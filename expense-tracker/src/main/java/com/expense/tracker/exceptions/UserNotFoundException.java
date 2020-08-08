package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;

public class UserNotFoundException extends ExpenseTrackerException {

	public UserNotFoundException(Long userId) {
		super(String.format("User ID '%s' not found! Please Register as new User.", userId.toString()), ErrorCode.USER_NOT_FOUND);
	}
}
