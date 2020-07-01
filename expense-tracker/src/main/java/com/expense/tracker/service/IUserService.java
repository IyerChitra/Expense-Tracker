package com.expense.tracker.service;

import com.expense.tracker.models.Pagination;
import com.expense.tracker.models.User;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface IUserService {

	User getUserDetails(Long userId);

	User createNewUser(User user);

	List<User> getUserList(String emailId, Long walletId, Pagination page);
}
