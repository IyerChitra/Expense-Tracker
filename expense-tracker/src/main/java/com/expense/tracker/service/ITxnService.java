package com.expense.tracker.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.Transaction;

@Component
public interface ITxnService {

	List<Transaction> getTxns(Long walletId, Pagination page);
	
	Transaction txnCredit(Transaction txn);
	
	Transaction txnDebit(Transaction txn);
}
