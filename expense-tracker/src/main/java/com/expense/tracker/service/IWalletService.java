package com.expense.tracker.service;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.Wallet;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IWalletService {

    ResponseEntity<Wallet> getWalletDetails(String emailId, String walletName) throws Exception;
    
    ResponseEntity<Wallet> createNewWallet(Wallet wallet) throws ExpenseTrackerException;
}
