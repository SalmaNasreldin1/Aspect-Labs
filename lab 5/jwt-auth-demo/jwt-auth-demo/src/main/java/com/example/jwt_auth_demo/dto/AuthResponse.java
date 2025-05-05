package com.example.jwt_auth_demo.dto;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

}
