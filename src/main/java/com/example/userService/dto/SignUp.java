package com.example.userService.dto;

import com.example.userService.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUp {
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
