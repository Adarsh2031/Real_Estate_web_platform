package org.example.repository;

import java.util.Optional;

import org.example.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    // Find user by username
    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    // Find user by email
    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}