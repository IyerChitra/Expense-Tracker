package com.expense.tracker.controller;

import com.expense.tracker.models.User;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
