package com.careerbridge.dto.response;

import java.util.List;

public class AuthResponse {

    private String token;
    private String tokenType;
    private Long userId;
    private String fullName;
    private String userName;
    private String email;
    private List<String> roles;
    public AuthResponse() {
    }

    public AuthResponse(
            String token,
            Long userId,
            String fullName,
            String userName,
            String email,
            List<String> roles) {

        this.token = token;
        this.tokenType = "Bearer";
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}