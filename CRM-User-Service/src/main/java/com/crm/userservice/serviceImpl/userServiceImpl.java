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
		if (isEmailAlreadyExists(user.getUserEmail())) {
			throw new IllegalArgumentException("Email is already taken!");
		}

		if (isUsernameAlreadyExists(user.getUserName())) {
			throw new IllegalArgumentException("Username is already taken!");
		}

		if (isPhoneNumberAlreadyExists(user.getNumber())) {
			throw new IllegalArgumentException("Phone number is already registered!");
		}

		String password = user.getUserPassword();
		user.setUserPassword(passwordEncoder.encode(password));
		user.setJoinDate(LocalDate.now());

		userRepository.save(user);

		return "User registered successfully!";
	}



	private boolean isEmailAlreadyExists(String email) {
		return userRepository.existsByUserEmail(email);
	}

	private boolean isUsernameAlreadyExists(String userName) {
		return userRepository.existsByUserName(userName);
	}

	private boolean isPhoneNumberAlreadyExists(String number) {
		return userRepository.existsByNumber(number);
	}




	@Override
	public User logInUser(String userName, String userPassword) {
		User user = userRepository.findByUserName(userName);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (user != null && passwordEncoder.matches(userPassword, user.getUserPassword())) {
			return user;
		} else {
			return null;
		}
	}

	@Override
	public User updateUser(User updateRequest) {
		User existingUser = userRepository.findByUserName(updateRequest.getUserName());

		if (existingUser != null) {
			if (updateRequest.getUserEmail() != null) {
				existingUser.setUserEmail(updateRequest.getUserEmail());
			}
			if (updateRequest.getNumber() != null) {
				existingUser.setNumber(updateRequest.getNumber());
			}
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			if (updateRequest.getUserPassword() != null && !updateRequest.getUserPassword().isEmpty()) {
				String hashedPassword = passwordEncoder.encode(updateRequest.getUserPassword());
				existingUser.setUserPassword(hashedPassword);
			}

			return userRepository.save(existingUser);
		} else {
			return null;
		}
	}
}


