package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;

public class TxnNotFoundException extends ExpenseTrackerException {

	public TxnNotFoundException(String txnId) {
		super(String.format("Unable to retrieve details for Transaction ID: '%s'! Please try again later.", txnId),
				ErrorCode.TXN_NOT_FOUND);
	}

	public TxnNotFoundException(Long walletId) {
		super(String.format("No transactions found for WalletID: '%s' for the given Time period!", walletId.toString()),
				ErrorCode.TXN_NOT_FOUND);
	}
}
