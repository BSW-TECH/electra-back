package com.electra.server.service;

import com.electra.server.config.security.jwt.JwtTokenUtils;
import com.electra.server.infrastructure.api.UserRepositoryApi;
import com.electra.server.service.api.AuthApi;
import com.electra.server.service.data.*;
import com.electra.server.service.mapper.UserServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService implements AuthApi {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserRepositoryApi userRepositoryApi;

    @Autowired
    private UserServiceMapper userServiceMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public JwtData register(RegisterData data) {
        final Optional<UserData> userDataOptional = userRepositoryApi.retrieveUser(data.getEmail());
        if (userDataOptional.isPresent()) {
            throw new BadCredentialsException("Username already exists");
        }
        final UserData userData = userRepositoryApi.createUser(userServiceMapper.toUserData(data));
        final UserAuthority userAuthority = userServiceMapper.toUserAuthority(userData);
        final String token = jwtTokenUtils.generateToken(userAuthority);
        return new JwtData(token);
    }

    @Override
    public JwtData login(LoginData data) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));

        UserDetails userData = userService.loadUserByUsername(data.getEmail());
        String token = jwtTokenUtils.generateToken(userData);
        return new JwtData(token);
    }
}
