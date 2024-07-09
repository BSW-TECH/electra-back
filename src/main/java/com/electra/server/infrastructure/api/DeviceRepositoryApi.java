package com.electra.server.infrastructure.api;

import com.electra.server.service.data.DeviceData;

import java.util.Optional;

public interface DeviceRepositoryApi {


    DeviceData add(DeviceData key);

    Optional<DeviceData> retrieve(String key);

}
