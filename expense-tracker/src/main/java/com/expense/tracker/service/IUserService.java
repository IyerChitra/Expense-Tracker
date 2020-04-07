package com.expense.tracker.service;

import com.expense.tracker.models.User;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {

	User getUserDetails(String userId);

	User createNewUser(User user);
}
