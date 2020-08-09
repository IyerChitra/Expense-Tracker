package com.expense.tracker.controller;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.Transaction;
import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expense-tracker/wallet")
public class WalletController {

	@Autowired
	IWalletService walletService;

	@GetMapping("")
	public @ResponseBody Wallet walletDetails(@RequestParam Long walletId, @RequestParam String walletName) {
		return walletService.getWalletDetails(walletId, walletName);
	}

	@PostMapping("/create")
	public @ResponseBody Wallet newUser(@RequestBody Wallet newWallet) {
		return walletService.createNewWallet(newWallet);
	}

	@PutMapping("/addUser/{walletId}")
	public @ResponseBody Long addUser(@RequestBody List<User> users, @PathVariable Long walletId) {
		return walletService.addUser(users, walletId);
	}

	@GetMapping("/transactions")
	public @ResponseBody List<Transaction> getTxnList(@RequestParam Long walletId, @RequestParam Long frmDate,
			@RequestParam Long toDate, @RequestBody Pagination page) {
		return walletService.getTxnList(walletId, frmDate, toDate, page);
	}

}
