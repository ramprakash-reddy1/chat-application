package com.chat.app.controller;

import com.chat.app.dto.ApiResponse;
import com.chat.app.model.User;
import com.chat.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(){
        List<User> allUsers = userService.findAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Fetched list of users",allUsers));
    }

}
