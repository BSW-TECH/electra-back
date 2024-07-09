package com.electra.server.service.api;

import com.electra.server.service.data.DeviceData;
import com.electra.server.service.data.JwtData;

public interface DeviceApi {

    JwtData add(DeviceData data);

    DeviceData get(String key);

}
