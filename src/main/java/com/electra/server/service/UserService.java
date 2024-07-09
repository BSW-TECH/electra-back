package com.electra.server.service;

import com.electra.server.infrastructure.api.UserRepositoryApi;
import com.electra.server.service.data.UserData;
import com.electra.server.service.mapper.UserServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepositoryApi userRepositoryApi;

    @Autowired
    private UserServiceMapper userServiceMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        final Optional<UserData> userData = userRepositoryApi.retrieveUser(username);
        if (userData.isEmpty()) {
            throw new BadCredentialsException(String.format("Username '%s' not found", username));
        }
        return userServiceMapper.toUserAuthority(userData.get());
    }
}