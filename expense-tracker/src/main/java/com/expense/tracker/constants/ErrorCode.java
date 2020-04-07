package com.expense.tracker.constants;

public enum ErrorCode {
  TECHNICAL_ERROR("Technical Error. Please Try again."),
  USER_NOT_FOUND("User ID Not Found! Please Register as new User."),
  WALLET_NOT_FOUND("Unable to retrieve Wallet."),
  DUPLICATE_USER("Email or Mobile already taken! Please register with another Email and Mobile"),
  DUPLICATE_KEY("Duplicate values not allowed!"),
  NULL_VALUE("Empty field values not permitted! Please enter valid details."),
  NO_SUCH_USER("User does not exist! Please invite the user to Sign Up.");

  private String errorDesc;

  ErrorCode(String errorDesc) {
    this.errorDesc = errorDesc;
  }

  public String getErrorDesc() {
    return errorDesc;
  }
}
