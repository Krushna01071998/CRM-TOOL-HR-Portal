package com.crm.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.userservice.model.User;
import com.crm.userservice.service.UserService;

@RestController
@RequestMapping("userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> signUpUser(@RequestBody User user) {

		try {

			return ResponseEntity.ok(userService.signUpUser(user));

		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
		}

	}

}
