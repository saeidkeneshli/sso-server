package io.github.saeidkeneshli.server.controller;

import lombok.RequiredArgsConstructor;
import io.github.saeidkeneshli.server.dto.AuthResponse;
import io.github.saeidkeneshli.server.dto.LoginRequest;
import io.github.saeidkeneshli.server.model.User;
import io.github.saeidkeneshli.server.security.TokenProvider;
import io.github.saeidkeneshli.server.service.UserService;
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
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;

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