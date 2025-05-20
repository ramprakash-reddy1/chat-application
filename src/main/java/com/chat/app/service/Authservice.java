package com.chat.app.service;

import com.chat.app.dto.AuthReponse;
import com.chat.app.dto.AuthRequest;
import com.chat.app.dto.RegisterRequest;

public interface Authservice {

    void register(RegisterRequest registerRequest);

    AuthReponse login(AuthRequest authRequest);
}
