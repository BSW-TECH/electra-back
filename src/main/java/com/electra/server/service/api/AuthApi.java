package com.electra.server.service.api;

import com.electra.server.service.data.JwtData;
import com.electra.server.service.data.LoginData;
import com.electra.server.service.data.RegisterData;

public interface AuthApi {

    JwtData register(RegisterData data);
    JwtData login(LoginData data);

}
