package com.crm.userservice.service;

import org.springframework.stereotype.Service;

import com.crm.userservice.model.User;

@Service
public interface UserService  {

	public String signUpUser(User user);
	public User logInUser(String userName, String userPassword);
	public User updateUser(User user);
}
