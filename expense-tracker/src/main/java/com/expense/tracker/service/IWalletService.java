package com.expense.tracker.service;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.Transaction;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IWalletService {

	Wallet getWalletDetails(Long walletId, String walletName);

	Wallet createNewWallet(Wallet wallet);

	Long addUser(List<User> users, Long walletId);

	List<Transaction> getTxnList(Long txnId, Long fromDate, Long toDate, Pagination page);
}
