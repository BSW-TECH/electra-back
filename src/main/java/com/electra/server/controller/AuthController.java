package com.electra.server.controller;

import com.electra.server.controller.mapper.AuthMapper;
import com.electra.server.controller.model.LoginRequest;
import com.electra.server.controller.model.RegisterRequest;
import com.electra.server.service.api.AuthApi;
import com.electra.server.service.data.JwtData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthApi authApi;

    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        final JwtData authResponse = authApi.register(authMapper.toRegisterData(request));
        return ResponseEntity.ok(authMapper.toJwtResponse(authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        final JwtData authResponse = authApi.login(authMapper.toLoginData(request));
        return ResponseEntity.ok(authMapper.toJwtResponse(authResponse));
    }
}