package com.crm.userservice.service;

import org.springframework.stereotype.Service;

import com.crm.userservice.model.User;

@Service
public interface UserService  {
	
	public User signUpUser(User user);

}
