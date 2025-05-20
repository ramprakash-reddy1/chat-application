package com.chat.app.service.impl;

import com.chat.app.advice.UserNotFoundException;
import com.chat.app.model.User;
import com.chat.app.repository.UserRepository;
import com.chat.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceIMPL implements UserService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceIMPL.class);

    private final UserRepository userRepository;

    public UserServiceIMPL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow( () -> {
                   LOGGER.error("userNotFound with this username: {}", username);
                  return  new UserNotFoundException("userNotFound with this username:" + username);
                });
    }
    @Override
    public List<User> findAllUsers() {
        List<User>users= userRepository.findAll();
        LOGGER.info("returned: {} users",users.size());
        return users;
    }
}
