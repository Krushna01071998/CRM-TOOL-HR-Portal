package com.crm.userservice.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.userservice.model.User;
import com.crm.userservice.repository.UserRepository;
import com.crm.userservice.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class userServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public String signUpUser(User user) {
		// Check if email is already taken
		if (isEmailAlreadyExists(user.getUserEmail())) {
			throw new IllegalArgumentException("Email is already takenasikfdchadshik!");
		}

		// Check if username is already taken
		if (isUsernameAlreadyExists(user.getUserName())) {
			throw new IllegalArgumentException("Username is already takenahdsklfhadsh!");
		}

		// Check if phone number is already registered
		if (isPhoneNumberAlreadyExists(user.getNumber())) {
			throw new IllegalArgumentException("Phone number is already registered!");
		}

		// Encode password and set join date
		String password = user.getUserPassword();
		user.setUserPassword(passwordEncoder.encode(password));
		user.setJoinDate(LocalDate.now());

		// Save user in the repository
		userRepository.save(user);

		return "User registered successfully!";
	}



	private boolean isEmailAlreadyExists(String email) {
		return userRepository.existsByUserEmail(email); // Correct method
	}

	private boolean isUsernameAlreadyExists(String userName) {
		return userRepository.existsByUserName(userName); // Correct method
	}

	private boolean isPhoneNumberAlreadyExists(String number) {
		return userRepository.existsByNumber(number); // Correct method
	}




	@Override
	public User logInUser(String userName, String userPassword) {
		// Find user by username
		User user = userRepository.findByUserName(userName);

		// Validate password (assuming plain-text here for simplicity, use hashing in production)
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Validate password (using hashed password comparison)
		if (user != null && passwordEncoder.matches(userPassword, user.getUserPassword())) {
			return user;
		} else {
			return null; // Invalid username/password
		}
	}

	@Override
	public User updateUser(User updateRequest) {
		// Retrieve user from the database using username (or user id)
		User existingUser = userRepository.findByUserName(updateRequest.getUserName());

		if (existingUser != null) {
			// Update the fields that are provided in the request
			if (updateRequest.getUserEmail() != null) {
				existingUser.setUserEmail(updateRequest.getUserEmail());
			}
			if (updateRequest.getNumber() != null) {
				existingUser.setNumber(updateRequest.getNumber());
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (updateRequest.getUserPassword() != null && !updateRequest.getUserPassword().isEmpty()) {
				// Hash the new password before saving
				String hashedPassword = passwordEncoder.encode(updateRequest.getUserPassword());
				existingUser.setUserPassword(hashedPassword);
			}

			// Save the updated user to the database
			return userRepository.save(existingUser);
		} else {
			// User not found
			return null;
		}
	}
}


