package com.electra.server.infrastructure.mapper;

import com.electra.server.infrastructure.entity.DeviceEntity;
import com.electra.server.infrastructure.entity.UserEntity;
import com.electra.server.service.data.DeviceData;
import com.electra.server.service.data.UserData;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryMapper {

    public UserData toUserData(UserEntity entity) {
        final UserData userData = new UserData();
        userData.setId(Optional.of(entity.getId()));
        userData.setEmail(entity.getEmail());
        userData.setPassword(entity.getPassword());

        final DeviceData deviceData = new DeviceData();
        deviceData.setId(Optional.of(entity.getDeviceEntity().getId()));
        deviceData.setDeviceKey(entity.getDeviceEntity().getKey());
        deviceData.setUserDataList(Optional.empty());

        userData.setDeviceData(Optional.of(deviceData));

        userData.setAuthority(Optional.of(entity.getRoleEntity()));

        return userData;
    }

    public DeviceData toDeviceData(DeviceEntity deviceEntity) {
        final DeviceData deviceData = new DeviceData();
        deviceData.setId(Optional.of(deviceEntity.getId()));
        deviceData.setDeviceKey(deviceEntity.getKey());

        if (deviceEntity.getUserEntities() != null && !deviceEntity.getUserEntities().isEmpty()) {
            deviceData.setUserDataList(
                    Optional.of(
                            deviceEntity
                                    .getUserEntities()
                                    .stream()
                                    .map(this::toDeviceUserData)
                                    .collect(Collectors.toList())));
        } else {
            deviceData.setUserDataList(Optional.empty());
        }

        return deviceData;
    }

    private UserData toDeviceUserData(UserEntity entity) {
        final UserData userData = new UserData();
        userData.setId(Optional.of(entity.getId()));
        userData.setEmail(entity.getEmail());
        return userData;
    }
}
