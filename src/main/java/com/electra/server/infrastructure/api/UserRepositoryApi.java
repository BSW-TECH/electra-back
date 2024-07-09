package com.electra.server.infrastructure.api;

import com.electra.server.service.data.UserData;

import java.util.Optional;

public interface UserRepositoryApi {

    UserData createUser(UserData data);
    Optional<UserData> retrieveUser(String email);

}