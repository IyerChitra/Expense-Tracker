package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;

public class UserNotFoundException extends ExpenseTrackerException {

  public UserNotFoundException(String var1) {
    super(var1, ErrorCode.USER_NOT_FOUND);
  }
}
