package com.chat.app.advice;

public class InvalidUsernameOrPasswordException  extends RuntimeException{
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
