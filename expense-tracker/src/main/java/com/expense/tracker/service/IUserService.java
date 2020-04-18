package com.expense.tracker.service;

import com.expense.tracker.exceptions.ExpenseTrackerException;
import com.expense.tracker.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {

  User getUserDetails(String userId) ;

  ResponseEntity<User> createNewUser(User user) throws ExpenseTrackerException;
}
