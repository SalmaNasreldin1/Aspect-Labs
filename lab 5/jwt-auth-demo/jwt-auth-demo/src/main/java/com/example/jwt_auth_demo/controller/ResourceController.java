package com.example.jwt_auth_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint, no authentication required.";
    }

    @GetMapping("/protected")
    @PreAuthorize("isAuthenticated()")
    public String protectedEndpoint() {
        return "This is a protected endpoint, authentication required.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminEndpoint() {
        return "This is an admin endpoint, ADMIN role required.";
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public String userEndpoint() {
        return "This is a user endpoint, authentication required.";
    }
}