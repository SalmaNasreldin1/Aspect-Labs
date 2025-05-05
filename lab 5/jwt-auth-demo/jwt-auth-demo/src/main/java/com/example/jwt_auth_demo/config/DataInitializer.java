package com.example.jwt_auth_demo.config;

import com.example.jwt_auth_demo.model.User;
import com.example.jwt_auth_demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create a regular user
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("password"));
            user.addRole("ROLE_USER");
            userRepository.save(user);

            // Create an admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.addRole("ROLE_USER");
            admin.addRole("ROLE_ADMIN");
            userRepository.save(admin);

            System.out.println("Test users created successfully!");
        };
    }
}