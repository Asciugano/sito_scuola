package com.asciugano.sito_scuola.controllers;

import com.asciugano.sito_scuola.config.SecurityConfig;
import com.asciugano.sito_scuola.models.User;
import com.asciugano.sito_scuola.repository.UserRepository;
import com.asciugano.sito_scuola.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        return  jwtService.generateToken(user.getEmail());
    }

    @PostMapping("/singup")
    public String singup(@RequestBody User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);

        return jwtService.generateToken(user.getEmail());
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(@RequestHeader("Authorization") String authHeader) {
        if(authHeader == null || authHeader.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid JWT token");
        }

        String token = authHeader.substring(7);
        String email;

        try {
            email = jwtService.extractUsername(token);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
        }

        if(jwtService.isValidToken(token, email)) {
            return ResponseEntity.ok("Token is valid");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired JWT token");
        }
    }
}

