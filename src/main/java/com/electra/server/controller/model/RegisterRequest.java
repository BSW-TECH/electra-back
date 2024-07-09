package com.electra.server.controller.model;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String password;
    private String deviceKey;

}
