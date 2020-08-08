package com.expense.tracker.service;

import com.expense.tracker.models.Transaction;
import org.springframework.stereotype.Component;

@Component
public interface ITxnService {

	Transaction getTxnDetails(Long txnId);

	Transaction txnCredit(Transaction txn);

	Transaction txnDebit(Transaction txn);
}
