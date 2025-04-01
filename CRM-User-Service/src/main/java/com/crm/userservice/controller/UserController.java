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
	@PostMapping("/login")
	public ResponseEntity<?> logInUser(@RequestBody User loginRequest) {
		System.out.println("Received login request: " + loginRequest); // Log the request object

		try {
			// Call the login method in the UserService
			User user = userService.logInUser(loginRequest.getUserName(), loginRequest.getUserPassword());
			if (user != null) {
				return ResponseEntity.ok("Login successful!");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
		}

	}

	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody User updateRequest) {
		try {
			// Call the service method to update user information
			User updatedUser = userService.updateUser(updateRequest);

			// Check if the user was successfully updated and return response
			if (updatedUser != null) {
				return ResponseEntity.ok("User information updated successfully!");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getMessage());
		}
	}


}
