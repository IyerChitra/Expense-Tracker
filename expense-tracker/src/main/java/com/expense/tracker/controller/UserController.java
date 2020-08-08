package com.expense.tracker.controller;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.User;
import com.expense.tracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense-tracker/user")
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("")
	public @ResponseBody User userDetails(@RequestParam Long userId) {
		return userService.getUserDetails(userId);
	}

	@PostMapping("/registration")
	public @ResponseBody User newUsser(@RequestBody User newUser) {
		return userService.createNewUser(newUser);
	}

	// TODO: email text search API for users., @RequestParam Pagination page
	@GetMapping("/search")
	public @ResponseBody List<User> getUserList(@RequestParam String emaildId, @RequestParam Long walletId) {
		
		Pagination page = new Pagination();
		page.setLimit(10);
		page.setOffset(0);
		return userService.getUserList(emaildId, walletId, page);
	}

}
