package com.crm.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.crm.userservice.model.User;

@Service
public interface UserService  {

	String signUpUser(User user);
	User logInUser(String userName, String userPassword);
	User updateUser(User user);
	 String deleteUser(User user);

	}

