package com.example.userService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username is required")
    @Column(nullable = false)
    private String username;

    //@NotNull for all collection but only for null
    //@NotEmpty allow white spaces and for all collections
    //@NotBlank only for string not null, empty or white spaces
    //@Email, @NotBlank is a part of Jakarta Validation API (standard)
    //hibernate validation is implementaion of Jakarta Validation API
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not correct")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "password is required")
    private String password; // stored as hash

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
