package com.example.jwt_auth_demo.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For a production application, this would query a database instead of using hardcoded users
        switch (username) {
            case "admin":
                return new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        List.of(
                                new SimpleGrantedAuthority("ROLE_ADMIN"),
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                );
            case "user":
                return new User(
                        "user",
                        passwordEncoder.encode("password"),
                        Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                );
            default:
                throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}