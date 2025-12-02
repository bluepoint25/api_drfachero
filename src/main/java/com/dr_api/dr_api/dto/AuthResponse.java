package com.dr_api.dr_api.dto;

public class AuthResponse {
    
    private final String jwt;
    private final String username;
    private final String role;

    public AuthResponse(String jwt, String username, String role) {
        this.jwt = jwt;
        this.username = username;
        this.role = role;
    }

    // Getters (para serialización a JSON)
    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}