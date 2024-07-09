package com.electra.server.service.data;

import lombok.Data;

@Data
public class RegisterData {

    private String email;
    private String password;
    private String deviceKey;

}