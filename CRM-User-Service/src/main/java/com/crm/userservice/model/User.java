package com.crm.userservice.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User")
public class User {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "email")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
	private String userEmail;

	@Column(name = "password")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "Password must be at least 8 characters long and contain both letters and digits.")
	private String userPassword;

	@Column(name = "number")
	@Pattern(regexp = "^[\\d\\s\\-\\(\\)]{10,15}$", message = "Must be between 10 and 15 digits with optional spaces, hyphens, or parentheses")
	private String number;


	@Column(name = "creationtime")
	private LocalDate joinDate;

	@Column(name = "role")
	private String role;

}