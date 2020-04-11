package com.expense.tracker.constants;

public enum ErrorCode {
  TECHNICAL_ERROR("Technical Error. Please Try again."),
  USER_NOT_FOUND("");

  private String errorDesc;

  ErrorCode(String errorDesc) {
    this.errorDesc = errorDesc;
  }

  public String getErrorDesc() {
    return errorDesc;
  }
}
