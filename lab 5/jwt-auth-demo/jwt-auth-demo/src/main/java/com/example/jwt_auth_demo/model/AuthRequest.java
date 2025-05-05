package com.example.jwt_auth_demo.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}