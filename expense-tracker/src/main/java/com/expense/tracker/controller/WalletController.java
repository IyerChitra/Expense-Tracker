package com.expense.tracker.controller;

import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense-tracker/user")
public class WalletController {

	@Autowired
	IWalletService walletService;

	@GetMapping("/wallet")
	public @ResponseBody Wallet walletDetails(@RequestParam Long walletId, @RequestParam String walletName) {
		return walletService.getWalletDetails(walletId, walletName);
	}

	@PostMapping("/createWallet")
	public @ResponseBody Wallet newUsser(@RequestBody Wallet newWallet) {
		return walletService.createNewWallet(newWallet);
	}

	@PostMapping("/wallet/addUser")
	public @ResponseBody Wallet addUser(@RequestBody Wallet wallet, @RequestParam String emailId) {
		return walletService.addUser(wallet, emailId);
	}

}
