package com.crm.userservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.crm.userservice.model.User;
import com.crm.userservice.service.UserService;


@RestController
@RequestMapping("userservice")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create-employee")
	public ResponseEntity<String> signUpUser(@Valid @RequestBody User user, BindingResult result) {
		String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		if (!currentUserRole.contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create new users.");
		}

		if (result.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder("Validation failed: ");
			result.getAllErrors().forEach(error ->
					errorMessage.append(error.getDefaultMessage()).append("; ")
			);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
		}

		try {

			return ResponseEntity.ok(userService.signUpUser(user));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
		}
	}
	@PostMapping("/create-employee")
	public ResponseEntity<String> signUpUser1(@Valid @RequestBody User user, BindingResult result) {
		String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		if (!currentUserRole.contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create new users.");
		}

		if (result.hasErrors()) {
			StringBuilder errorMessage = new StringBuilder("Validation failed: ");
			result.getAllErrors().forEach(error ->
					errorMessage.append(error.getDefaultMessage()).append("; ")
			);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
		}

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

	@PostMapping("/update-employee")
	public ResponseEntity<?> updateUser(@RequestBody User updateRequest) {
		String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		if (!currentUserRole.contains("ROLE_EMPLOYEE")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only employee can update.");
		}
			String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

			// User can only update their own details
			if (!updateRequest.getUserName().equals(currentUserName)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can only update your own details.");
			}

		try {
			User updatedUser = userService.updateUser(updateRequest);

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


	@DeleteMapping ("/delete-employee")
	public ResponseEntity<String> deleteUser(@RequestBody User deleteRequest) {
		String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		if (!currentUserRole.contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can delete user accounts.");
		}

		try {
			String deleteResponse = userService.deleteUser(deleteRequest);
			if (deleteResponse.equals("User Deleted Successfully")) {
				return ResponseEntity.ok(deleteResponse);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the user.");
		}
    }


}
