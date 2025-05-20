package com.chat.app.service;

import com.chat.app.model.User;

import java.util.List;

public interface UserService {

    User loadUserByUsername(String username);

    List<User> findAllUsers();
}
