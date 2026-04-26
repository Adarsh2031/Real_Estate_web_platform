package org.example.security;

import java.time.Duration;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;

public class JwtUtil {

    public static String generateToken(String username) {
        return Jwt.issuer("real-estate-app")
                .subject(username)
                .groups(Set.of("user"))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }
}