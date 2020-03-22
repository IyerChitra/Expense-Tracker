package com.project.name.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.name.model.User;


@RestController
public class UserController {
	User user = new User();
	
	 @GetMapping("/users/{email}")
	    private User userDetails(@RequestParam String email) {
	        return user;
	    }

	 @PostMapping("/registration/user")
	  User newUsser(@RequestBody User newUser) {
		 /*
		  * Things to do: (Already checked if the user present or not in my Javascript???)
		  * 1. save the details to my database.
		  */
	    return newUser;
	  }
}
