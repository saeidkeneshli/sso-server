package org.example.controller;

import org.example.dto.AuthResponse;
import org.example.dto.LoginRequest;
import org.example.model.User;
import org.example.security.TokenProvider;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the authenticated user details
        User user = userService.findByUsername(loginRequest.getUsername()).orElse(null);

        if (user == null) {
             return ResponseEntity.badRequest().body(new AuthResponse("User not found"));
        }

        // Generate a new JWT for the authenticated user
        String token = tokenProvider.createToken(authentication);

        // Return the token to the client
        return ResponseEntity.ok(new AuthResponse(token));
    }
}