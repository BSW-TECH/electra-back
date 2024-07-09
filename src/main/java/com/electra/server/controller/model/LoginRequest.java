package com.electra.server.controller.model;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
