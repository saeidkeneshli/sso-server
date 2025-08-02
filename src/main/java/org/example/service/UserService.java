package org.example.service;

import org.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final Map<String, User> userDatabase = new HashMap<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService() {
        // In-memory user data for this example
        userDatabase.put("user1", new User("user1", passwordEncoder.encode("pass123")));
        userDatabase.put("admin", new User("admin", passwordEncoder.encode("adminpass")));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userDatabase.get(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}