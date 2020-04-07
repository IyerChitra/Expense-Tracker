package com.expense.tracker.controller;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.Wallet;
import com.expense.tracker.service.IWalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense-tracker/user")
public class WalletController {

  @Autowired IWalletService walletService;

  @GetMapping("/wallet")
  public @ResponseBody ResponseEntity<Wallet> walletDetails(@RequestParam String emailId, @RequestParam String walletName) throws Exception {
    return walletService.getWalletDetails(emailId, walletName);
  }

  @PostMapping("/createWallet")
  public @ResponseBody ResponseEntity<Wallet> newUsser(@RequestBody Wallet newWallet) throws ExpenseTrackerException {
    return walletService.createNewWallet(newWallet);
  }
  
}
