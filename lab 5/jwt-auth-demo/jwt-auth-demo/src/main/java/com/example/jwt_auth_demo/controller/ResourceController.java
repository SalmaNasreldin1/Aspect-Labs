package com.example.jwt_auth_demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint - no JWT required");
    }

    @GetMapping("/protected")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("You have successfully accessed a protected endpoint!");
    }

    @GetMapping("/admin")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("This is the admin area - also protected!");
    }
}