package com.example.jwt_auth_demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For simplicity, we're just using a hardcoded user
        // In a real application, you would look up the user from a database
        if ("user".equals(username)) {
            return new User(
                    "user",
                    passwordEncoder.encode("password"),
                    Collections.emptyList()
            );
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}