package com.expense.tracker.exceptions;

public class WalletNotFoundException extends ExpenseTrackerException {

  public WalletNotFoundException(final String walletName) {
    super(String.format("Unable to retrieve Wallet : '%s'! Please try again later.", walletName));
  }
}
