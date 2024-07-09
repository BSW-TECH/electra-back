package com.electra.server.service.data;

import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserAuthority extends User {

    public UserAuthority(UserData data) {
        super(
                data.getEmail(),
                data.getPassword(),
                true,
                true,
                true,
                true,
                List.of(data.getAuthority().get()));
    }

}

