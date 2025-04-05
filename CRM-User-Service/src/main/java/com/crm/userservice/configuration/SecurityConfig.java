package com.crm.userservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//	@Autowired
//	private final UserDetailsServiceImpl userDetailsService;
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
//		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService) // Use the UserDetailsService you created
//				.passwordEncoder(passwordEncoder());  // Use the password encoder for password matching
//	}
}