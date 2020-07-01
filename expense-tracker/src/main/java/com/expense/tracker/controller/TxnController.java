package com.expense.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.Transaction;
import com.expense.tracker.service.ITxnService;

@RestController
@RequestMapping("/expense-tracker/txn")
public class TxnController {
	
	@Autowired
	ITxnService txnService;

	@GetMapping("")
	public @ResponseBody List<Transaction> getTxns(@RequestParam Long walletId, @RequestBody Pagination page){
		return txnService.getTxns(walletId, page);
	}
	
	@PostMapping("/credit")
	public @ResponseBody Transaction txnCredit(@RequestBody Transaction txn){
		return txnService.txnCredit(txn);
	}
	
	@PostMapping("/debit")
	public @ResponseBody Transaction txnDebit(@RequestBody Transaction txn){
		return txnService.txnDebit(txn);
	}
	
}
