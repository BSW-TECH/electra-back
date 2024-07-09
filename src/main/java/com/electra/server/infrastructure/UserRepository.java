package com.electra.server.infrastructure;

import com.electra.server.exception.BadRequestException;
import com.electra.server.infrastructure.api.UserRepositoryApi;
import com.electra.server.infrastructure.crud.DeviceRepositoryCrud;
import com.electra.server.infrastructure.crud.RoleRepositoryCrud;
import com.electra.server.infrastructure.crud.UserRepositoryCrud;
import com.electra.server.infrastructure.entity.DeviceEntity;
import com.electra.server.infrastructure.entity.RoleEntity;
import com.electra.server.infrastructure.entity.UserEntity;
import com.electra.server.infrastructure.mapper.UserRepositoryMapper;
import com.electra.server.service.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepository implements UserRepositoryApi {

    private final static String NEW_USER_ROLE = "ACTIVE_USER";

    @Autowired
    private UserRepositoryCrud userCrud;

    @Autowired
    private DeviceRepositoryCrud deviceCrud;

    @Autowired
    private RoleRepositoryCrud roleCrud;

    @Autowired
    private UserRepositoryMapper userRepositoryMapper;

    @Override
    public UserData createUser(UserData data) {
        if (data.getDeviceData().isEmpty()) {
            throw new BadRequestException("Missing device key");
        }

        final Optional<DeviceEntity> deviceEntity = deviceCrud.findByKey(data.getDeviceData().get().getDeviceKey());
        if (deviceEntity.isEmpty()) {
            throw new BadRequestException("Invalid device key");
        }

        final Optional<RoleEntity> roleEntity = roleCrud.findByName(NEW_USER_ROLE);
        if (roleEntity.isEmpty()) {
            throw new RuntimeException("New user ROLE is not defined");
        }

        final UserEntity entity = userCrud.findByEmail(data.getEmail()).orElse(new UserEntity());
        entity.setEmail(data.getEmail());
        entity.setPassword(data.getPassword());
        entity.setDeviceEntity(deviceEntity.get());
        entity.setRoleEntity(roleEntity.get());

        return userRepositoryMapper.toUserData(userCrud.save(entity));
    }

    @Override
    public Optional<UserData> retrieveUser(String email) {
        final Optional<UserEntity> userFound = userCrud.findByEmail(email);
        return userFound.map(entity -> userRepositoryMapper.toUserData(entity));
    }

}
