package com.expense.tracker.exceptions;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(final String userId) {
        super(String.format("User ID '%s' not found! Please Register as new User.", userId));
    }
}
