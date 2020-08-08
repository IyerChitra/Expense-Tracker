package com.expense.tracker.constants;

public enum ErrorCode {
	TECHNICAL_ERROR("Technical Error. Please Try again."), USER_NOT_FOUND(
			"User ID Not Found! Please Register as new User."), WALLET_NOT_FOUND(
					"Unable to retrieve Wallet."), TXN_NOT_FOUND(
			"Unable to retrieve Txn Details."),DUPLICATE_USER(
							"Email or Mobile already taken! Please register with another Email and Mobile"), DUPLICATE_KEY(
									"Duplicate values not allowed!"), NULL_VALUE(
											"Empty field values not permitted! Please enter valid details."), NO_SUCH_USER(
													"User does not exist! Please invite the user to Sign Up."), INSUFFICIENT_BALANCE("Wallet balance is Insufficient! Please debit lesser amount."), INVALID_TRANSACTION("Invalid Transaction! Only CREDIT or DEBIT allowed"), UNAUTHORIZED_TXN("Unauthorised Transaction! You do not have access to this wallet."), NO_TXN_FOUND("No transactions found in the Wallet for the selected Time period!");

	private String errorDesc;

	ErrorCode(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorDesc() {
		return errorDesc;
	}
}

