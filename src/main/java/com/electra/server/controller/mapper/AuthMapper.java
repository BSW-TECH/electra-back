package com.electra.server.controller.mapper;

import com.electra.server.controller.model.JwtResponse;
import com.electra.server.controller.model.LoginRequest;
import com.electra.server.controller.model.RegisterRequest;
import com.electra.server.service.data.JwtData;
import com.electra.server.service.data.LoginData;
import com.electra.server.service.data.RegisterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterData toRegisterData(RegisterRequest request) {
        final RegisterData data = new RegisterData();
        data.setEmail(request.getEmail());
        data.setPassword(passwordEncoder.encode(request.getPassword()));
        data.setDeviceKey(request.getDeviceKey());
        return data;
    }

    public LoginData toLoginData(LoginRequest request) {
        return new LoginData(request.getEmail(), request.getPassword());
    }

    public JwtResponse toJwtResponse(JwtData token) {
        return new JwtResponse(token.getToken());
    }
}
