package com.electra.server.service.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Optional;

@Data
public class UserData {
    private Optional<Long> id;
    private String email;
    private String password;
    private Optional<DeviceData> deviceData;
    private Optional<GrantedAuthority> authority;
}
