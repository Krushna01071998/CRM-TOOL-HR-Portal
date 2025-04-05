package com.crm.userservice.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.userservice.model.User;
import com.crm.userservice.repository.UserRepository;
import com.crm.userservice.service.UserService;


@Service
public class userServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
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
		User exsistingUser = userRepository.findByUserName(userName);


		if (exsistingUser != null && passwordEncoder.matches(userPassword, exsistingUser.getUserPassword())) {
			return exsistingUser;
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

			if (updateRequest.getUserPassword() != null && !updateRequest.getUserPassword().isEmpty()) {
				existingUser.setUserPassword( passwordEncoder.encode(updateRequest.getUserPassword()));
			}

			return userRepository.save(existingUser);
		} else {
			return null;
		}
	}

	@Override
	public String deleteUser(User deleteRequest) {
		User existingUser= userRepository.findByUserName(deleteRequest.getUserName());
		if (existingUser != null){
			userRepository.deleteByUserName(existingUser.getUserName());
			return "User Deleted Successfully";
		}else {
			return "User not found";
		}
	}

}


