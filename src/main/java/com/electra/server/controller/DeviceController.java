package com.electra.server.controller;

import com.electra.server.controller.mapper.DeviceMapper;
import com.electra.server.controller.model.AddDeviceRequest;
import com.electra.server.controller.model.RegisterRequest;
import com.electra.server.service.api.DeviceApi;
import com.electra.server.service.data.JwtData;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceApi deviceApi;

    @Autowired
    private DeviceMapper deviceMapper;

    @PostMapping("/add")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> add(@RequestBody AddDeviceRequest request) {
        final JwtData authResponse = deviceApi.add(deviceMapper.toDeviceData(request));
        return ResponseEntity.ok(deviceMapper.toJwtResponse(authResponse));
    }

}
