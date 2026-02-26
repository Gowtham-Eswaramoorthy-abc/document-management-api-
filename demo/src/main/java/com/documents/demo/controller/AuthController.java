package com.documents.demo.controller;

import com.documents.demo.entity.User;
import com.documents.demo.repository.UserRepository;
import com.documents.demo.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository repo,
                          PasswordEncoder encoder,
                          JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        repo.save(user);

        return "User created";
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestBody User request) {

        User admin = repo.findByEmail(request.getEmail())
                .orElseThrow();

        if (admin.getRole() != User.Role.ADMIN) {
            throw new RuntimeException("Not admin");
        }

        if (!encoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());
    }
}