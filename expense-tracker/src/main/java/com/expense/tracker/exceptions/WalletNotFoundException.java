package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;

public class WalletNotFoundException extends ExpenseTrackerException {

	public WalletNotFoundException(final String walletName) {
		super(String.format("Unable to retrieve Wallet : '%s'! Please try again later.", walletName),
				ErrorCode.WALLET_NOT_FOUND);
	}
}
