package com.chat.app.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;

    private String password;

    private String profilePictureUrl;
}
