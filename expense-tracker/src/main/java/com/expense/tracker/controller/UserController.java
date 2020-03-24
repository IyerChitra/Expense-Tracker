package com.expense.tracker.controller;

import com.expense.tracker.models.User;
import com.expense.tracker.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense-tracker")
public class UserController {

  @Autowired IUserService userService;

  @GetMapping("/user")
  public @ResponseBody User userDetails(@RequestParam Long userId) {
    return userService.getUserDetails(userId);
  }

  @PostMapping("/registration/user")
  public @ResponseBody User newUsser(@RequestBody User newUser) {
    return newUser;
  }
}
