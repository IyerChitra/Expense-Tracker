package com.expense.tracker.service;

import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface IWalletService {

	Wallet getWalletDetails(Long walletId, String walletName);

	Wallet createNewWallet(Wallet wallet);

	Long addUser(List<User> users, Long walletId);
}
