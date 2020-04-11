package com.expense.tracker.service;

import com.expense.tracker.models.BaseResponse;
import com.expense.tracker.models.User;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {

    BaseResponse<User> getUserDetails(String userId);
    
    BaseResponse<User> createNewUser(User user);
}
