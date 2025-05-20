package com.chat.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthReponse {

    private String token;
    private String userId;
    private String username;

    public AuthReponse() {

    }
}