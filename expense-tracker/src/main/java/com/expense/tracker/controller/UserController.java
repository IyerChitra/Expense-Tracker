package com.expense.tracker.controller;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.BaseResponse;
import com.expense.tracker.models.User;
import com.expense.tracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense-tracker")
public class UserController {

  @Autowired IUserService userService;

  @GetMapping("/user")
  public @ResponseBody ResponseEntity<User> userDetails(@RequestParam String emailId) throws Exception {
    return userService.getUserDetails(emailId);
  }

  @PostMapping("/registration/user")
  public @ResponseBody ResponseEntity<User> newUsser(@RequestBody User newUser) throws ExpenseTrackerException {
    return userService.createNewUser(newUser);
  }
}
