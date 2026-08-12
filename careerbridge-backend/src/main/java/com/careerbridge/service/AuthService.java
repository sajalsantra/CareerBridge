package com.careerbridge.service;

import com.careerbridge.dto.auth.LoginRequest;
import com.careerbridge.dto.auth.RegisterRequest;
import com.careerbridge.dto.response.AuthResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}