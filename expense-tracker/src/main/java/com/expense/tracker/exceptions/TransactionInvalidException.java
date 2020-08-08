package com.expense.tracker.exceptions;

import com.expense.tracker.constants.ErrorCode;
import com.expense.tracker.models.Wallet;

public class TransactionInvalidException extends ExpenseTrackerException {

    public TransactionInvalidException(String txnType) {
        super(String.format("Invalid Transaction Type : '%s'. Allowed transactions are CREDIT and DEBIT", txnType), ErrorCode.INVALID_TRANSACTION);
    }
    public TransactionInvalidException(Wallet wallet) {
        super(String.format("Unauthorized Transaction! : You do not have access to the Wallet '%s'.", wallet.getWalletName()), ErrorCode.UNAUTHORIZED_TXN);
    }
    public TransactionInvalidException(Long walletAmount) {
        super(String.format("Insufficient Balance in Wallet! Maximum Debit possible is: '%s'.", walletAmount), ErrorCode.INSUFFICIENT_BALANCE);
    }





}
