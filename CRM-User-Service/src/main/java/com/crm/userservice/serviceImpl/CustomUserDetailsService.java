package com.crm.userservice.serviceImpl;

import com.crm.userservice.model.User;
import com.crm.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username (case-sensitive)
        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Return a Spring Security user object with roles and password
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getUserPassword(),  // Encoded password will be compared at login time
                getAuthorities(user)     // Get authorities (roles) for access control
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // Here, we convert the role to "ROLE_" prefix and make it a GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
}