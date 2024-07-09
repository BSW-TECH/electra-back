package com.electra.server.infrastructure;

import com.electra.server.exception.BadRequestException;
import com.electra.server.infrastructure.api.DeviceRepositoryApi;
import com.electra.server.infrastructure.crud.DeviceRepositoryCrud;
import com.electra.server.infrastructure.entity.DeviceEntity;
import com.electra.server.infrastructure.mapper.UserRepositoryMapper;
import com.electra.server.service.data.DeviceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeviceRepository implements DeviceRepositoryApi {

    @Autowired
    private DeviceRepositoryCrud deviceCrud;

    @Autowired
    private UserRepositoryMapper mapper;

    @Override
    public Optional<DeviceData> retrieve(String key) {
        final Optional<DeviceEntity> userFound = deviceCrud.findByKey(key);
        return userFound.map(entity -> mapper.toDeviceData(entity));
    }

    @Override
    public DeviceData add(DeviceData data) {
        if (deviceCrud.findByKey(data.getDeviceKey()).isPresent()) {
            throw new BadRequestException("Device already exists");
        }
        final DeviceEntity entity = new DeviceEntity();
        entity.setKey(data.getDeviceKey());
        return mapper.toDeviceData(deviceCrud.save(entity));
    }

}
