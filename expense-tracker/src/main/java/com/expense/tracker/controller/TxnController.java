package com.expense.tracker.controller;

import com.expense.tracker.models.Transaction;
import com.expense.tracker.service.ITxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense-tracker/txn")
public class TxnController {
	
	@Autowired
	ITxnService txnService;

@GetMapping("")
public @ResponseBody Transaction getTxnDetails(@RequestParam Long txnId){
	return txnService.getTxnDetails(txnId);
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
