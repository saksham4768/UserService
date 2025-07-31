package com.example.userService.service;

import com.example.userService.dto.AuthResponse;
import com.example.userService.dto.Login;
import com.example.userService.dto.SignUp;
import com.example.userService.entity.User;

public interface UserService {
    String registerUser(SignUp userData);
    AuthResponse loginUser(Login userData);
    User getProfile(String email);
}

