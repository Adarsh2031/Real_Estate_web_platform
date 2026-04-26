package org.example.service;

import java.util.Optional;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.security.JwtUtil;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    // Register new user
    public User register(User user) {

        // Check if username exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email exists
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Save user
        userRepository.persist(user);
        return user;
    }

    // Login user and return JWT token
    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT
        return JwtUtil.generateToken(username);
    }
}