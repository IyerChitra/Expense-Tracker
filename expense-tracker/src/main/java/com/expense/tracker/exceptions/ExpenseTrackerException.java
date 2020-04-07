package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;

public class ExpenseTrackerException extends RuntimeException {

	private ErrorCode err;

	public ExpenseTrackerException() {
	}

	public ExpenseTrackerException(String var1, ErrorCode err) {
		super(var1);
		this.err = err;
	}

	public ExpenseTrackerException(String message) {
		super(message);
	}

	public ErrorCode getErr() {
		return err;
	}
}
