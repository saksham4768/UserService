package com.example.userService.controller;

import com.example.userService.dto.AuthResponse;
import com.example.userService.dto.Login;
import com.example.userService.dto.SignUp;
import com.example.userService.entity.User;
import com.example.userService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUp req) {
        return ResponseEntity.ok(userService.registerUser(req));
    }

    @PostMapping("/admin")
    public ResponseEntity<String> allGood(@RequestBody SignUp req){
        return ResponseEntity.ok("Application is working fine");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Login req) {
        return ResponseEntity.ok(userService.loginUser(req));
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(Authentication auth) {
        String email = auth.getName(); // comes from JWT token, only access authentication when you protect the url otherwise not acess the authentication object
        return ResponseEntity.ok(userService.getProfile(email));
    }
}
