package com.chat.app.controller;

import com.chat.app.dto.ApiResponse;
import com.chat.app.dto.AuthReponse;
import com.chat.app.dto.AuthRequest;
import com.chat.app.dto.RegisterRequest;
import com.chat.app.service.Authservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final Authservice authservice;

    public AuthController(Authservice authservice) {
        this.authservice = authservice;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        authservice.register(registerRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthReponse>> login(@RequestBody AuthRequest authRequest){
        AuthReponse authReponse = authservice.login(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(HttpStatus.OK.value(),"User logged in successfully",authReponse));
    }
}
