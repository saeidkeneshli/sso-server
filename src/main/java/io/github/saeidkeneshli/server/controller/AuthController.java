package io.github.saeidkeneshli.server.controller;

import io.github.saeidkeneshli.server.dto.register.RegisterRequest;
import io.github.saeidkeneshli.server.dto.register.RegisterResponse;
import io.github.saeidkeneshli.server.util.CredentialUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import io.github.saeidkeneshli.server.dto.AuthResponse;
import io.github.saeidkeneshli.server.security.TokenProvider;
import io.github.saeidkeneshli.server.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static io.github.saeidkeneshli.server.util.CredentialUtil.decodeBasicAuth;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request) {

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Basic ")) {
            return ResponseEntity.badRequest().body("Missing Basic Authorization header");
        } //TODO: Clear this part

        CredentialUtil.BasicCredentials credentials = decodeBasicAuth(auth);

        String username = credentials.username();
        String password = credentials.password();

        // Authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a new JWT for the authenticated user
        String token = tokenProvider.createToken(authentication);

        // Return the token to the client
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerNewUser(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = userService.registerNewUser(registerRequest);
        if (!registerResponse.isAccepted()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(registerResponse);
        }
        return ResponseEntity.ok(registerResponse);
    }
}
