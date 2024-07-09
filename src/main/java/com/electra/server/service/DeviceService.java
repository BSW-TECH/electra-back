package com.electra.server.service;

import com.electra.server.config.security.jwt.JwtTokenUtils;
import com.electra.server.exception.BadRequestException;
import com.electra.server.infrastructure.api.DeviceRepositoryApi;
import com.electra.server.service.api.DeviceApi;
import com.electra.server.service.data.DeviceData;
import com.electra.server.service.data.JwtData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DeviceService implements DeviceApi {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private DeviceRepositoryApi deviceRepositoryApi;

    @Override
    @Transactional
    public JwtData add(DeviceData data) {
        final DeviceData deviceData = deviceRepositoryApi.add(data);
        String token = jwtTokenUtils.generateDeviceToken(deviceData);
        return new JwtData(token);
    }

    public DeviceData get(String key) {
        final Optional<DeviceData> deviceData = deviceRepositoryApi.retrieve(key);
        if (deviceData.isEmpty()) {
            throw new BadRequestException("Invalid device key");
        }
        return deviceData.get();
    }
}
