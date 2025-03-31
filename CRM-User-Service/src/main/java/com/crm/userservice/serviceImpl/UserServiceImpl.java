package com.crm.userservice.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.crm.userservice.model.User;
import com.crm.userservice.repository.UserRepository;
import com.crm.userservice.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User signUpUser(User user) {

		boolean isuserAvailable = userRepository.findByUserName(user.getUserName()) != null ? true : false;

		if (isuserAvailable) {
			throw new IllegalArgumentException("Username is already taken");
		}

		boolean isEmailAvailable = userRepository.findByUserEmail(user.getUserEmail()) != null ? true : false;

		if (isEmailAvailable) {
			throw new IllegalArgumentException("Email id is already registered");
		}

		boolean isNumberAvailable = userRepository.findByNumber(user.getNumber()) != null ? true : false;

		if (isNumberAvailable) {
			throw new IllegalArgumentException("Mobile number is already registered");
		}

		// password encoding
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		user.setCreationTime(LocalDate.now());
		return userRepository.save(user);
	}

}
