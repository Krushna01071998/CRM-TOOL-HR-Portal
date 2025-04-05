package com.crm.userservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.authorizeHttpRequests(request -> request
						.requestMatchers("/userservice/create-employee").hasAnyRole("ADMIN","MANAGER")
						.requestMatchers("/userservice/delete").hasRole("ADMIN")
						.requestMatchers("/userservice/update").hasRole("EMPLOYEE")
						.requestMatchers("/userservice/login").permitAll()
						.requestMatchers("/userservice/**").authenticated()
						.anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}
}