package com.electra.server.controller.mapper;

import com.electra.server.controller.model.AddDeviceRequest;
import com.electra.server.controller.model.JwtResponse;
import com.electra.server.service.data.DeviceData;
import com.electra.server.service.data.JwtData;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public DeviceData toDeviceData(AddDeviceRequest request) {
        final DeviceData data = new DeviceData();
        data.setDeviceKey(request.getKey());
        return data;
    }

    public JwtResponse toJwtResponse(JwtData response) {
        return new JwtResponse(response.getToken());
    }
}
