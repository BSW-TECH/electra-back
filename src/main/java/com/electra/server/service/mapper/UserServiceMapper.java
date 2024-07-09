package com.electra.server.service.mapper;

import com.electra.server.service.data.DeviceData;
import com.electra.server.service.data.RegisterData;
import com.electra.server.service.data.UserAuthority;
import com.electra.server.service.data.UserData;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceMapper {

    public UserData toUserData(RegisterData registerData) {
        final UserData data = new UserData();
        data.setId(Optional.empty());
        data.setEmail(registerData.getEmail());
        data.setPassword(registerData.getPassword());

        final DeviceData deviceData = new DeviceData();
        deviceData.setId(Optional.empty());
        deviceData.setDeviceKey(registerData.getDeviceKey());
        deviceData.setUserDataList(Optional.empty());

        data.setDeviceData(Optional.of(deviceData));
        return data;
    }

    public UserAuthority toUserAuthority(UserData userData) {
        return new UserAuthority(userData);
    }

}
