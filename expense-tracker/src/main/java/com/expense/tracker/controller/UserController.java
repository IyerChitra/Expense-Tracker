package com.expense.tracker.controller;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.User;
import com.expense.tracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense-tracker")
public class UserController {

  @Autowired IUserService userService;

  @GetMapping("/user")
  public @ResponseBody User userDetails(@RequestParam String emailId){
    return userService.getUserDetails(emailId);
  }

  @PostMapping("/registration/user")
  public @ResponseBody ResponseEntity<User> newUsser(@RequestBody User newUser)
      throws ExpenseTrackerException {
    return userService.createNewUser(newUser);
  }
}
