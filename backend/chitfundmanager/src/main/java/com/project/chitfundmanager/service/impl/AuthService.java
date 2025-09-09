package com.project.chitfundmanager.service.impl;

import com.project.chitfundmanager.dto.authDTO.LoginRequest;
import com.project.chitfundmanager.dto.authDTO.LoginResponse;
import com.project.chitfundmanager.dto.authDTO.RegistrationResponse;
import com.project.chitfundmanager.dto.authDTO.RegistrationRequest;

public interface AuthService {
    RegistrationResponse register(RegistrationRequest request);
    LoginResponse login(LoginRequest request);
}
