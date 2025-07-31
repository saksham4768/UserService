package com.example.userService.service;

import com.example.userService.config.JwtUtil;
import com.example.userService.dto.AuthResponse;
import com.example.userService.dto.Login;
import com.example.userService.dto.SignUp;
import com.example.userService.entity.Role;
import com.example.userService.entity.User;
import com.example.userService.repository.RoleRepository;
import com.example.userService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RoleRepository roleRepository;
    @Override
    public String registerUser(SignUp userData) {
        if(userRepository.findByEmail(userData.getEmail()).isPresent()){
            throw new RuntimeException("User already exist");
        }
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        User user = modelMapper.map(userData, User.class);
        System.out.println("Role is -> "+ userData.getRoles());
        Set<Role> fetchedRoles = userData.getRoles().stream()
                .map(role -> roleRepository.findByRole(role.getRole())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getRole())))
                .collect(Collectors.toSet());

        user.setRoles(fetchedRoles);
        userRepository.save(user);
        return "User registered";
    }

    @Override
    public AuthResponse loginUser(Login userData) {
        User user = userRepository.findByEmail(userData.email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Email"));

        if(!passwordEncoder.matches(userData.password, user.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        AuthResponse authResponse = new AuthResponse();
        authResponse.token = token;
        authResponse.username = user.getUsername();
        return authResponse;
    }

    @Override
    public User getProfile(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
