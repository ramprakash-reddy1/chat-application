package com.chat.app.service.impl;

import com.chat.app.advice.InvalidUsernameOrPasswordException;
import com.chat.app.advice.UsernameAlreadyTakenException;
import com.chat.app.config.JwtService;
import com.chat.app.dto.AuthReponse;
import com.chat.app.dto.AuthRequest;
import com.chat.app.dto.RegisterRequest;
import com.chat.app.model.User;
import com.chat.app.repository.UserRepository;
import com.chat.app.service.Authservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceIMPL implements Authservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceIMPL.class);

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;


    public AuthServiceIMPL(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            LOGGER.error("username already taken: {}",registerRequest.getUsername());
            throw new UsernameAlreadyTakenException("Username already taken");
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
    }

    @Override
    public AuthReponse login(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> {
                    LOGGER.error("invalid username: {}",authRequest.getUsername());
                    return new InvalidUsernameOrPasswordException("invalid username or password");
                });

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            LOGGER.error("invalid password: {}",authRequest.getPassword());
            throw new InvalidUsernameOrPasswordException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        AuthReponse response = new AuthReponse();
        response.setToken(token);
        response.setUserId(user.getUserId().toString());
        response.setUsername(user.getUsername());
        return response;
    }
}
