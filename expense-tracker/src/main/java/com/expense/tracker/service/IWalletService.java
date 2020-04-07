package com.expense.tracker.service;

import com.expense.tracker.models.Wallet;
import org.springframework.stereotype.Component;

@Component
public interface IWalletService {

	Wallet getWalletDetails(Long walletId, String walletName);

	Wallet createNewWallet(Wallet wallet);

	Wallet addUser(Wallet wallet, String emailId);
}
