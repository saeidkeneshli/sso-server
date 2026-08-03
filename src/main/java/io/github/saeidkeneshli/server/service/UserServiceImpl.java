package io.github.saeidkeneshli.server.service;

import io.github.saeidkeneshli.server.dto.register.RegisterRequest;
import io.github.saeidkeneshli.server.dto.register.RegisterResponse;
import io.github.saeidkeneshli.server.model.Users;
import io.github.saeidkeneshli.server.repository.UsersRepository;
import io.github.saeidkeneshli.server.util.Empty;
import io.github.saeidkeneshli.server.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final Map<String, Users> userDatabase = new HashMap<>();
    private final UsersRepository usersRepository;


    public Optional<Users> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), Collections.emptyList());
    }

    @Override
    public RegisterResponse registerNewUser(RegisterRequest registerRequest) {
        if (usersRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            System.out.println("user already registered.");
            return null;
        }

        String hashedPassword = PasswordUtil.hash(registerRequest.getPassword());

        Users users = Users.builder()
                .username(registerRequest.getUsername())
                .password(hashedPassword)
                .phoneNumber(registerRequest.getPhoneNumber())
                .email(Empty.isEmpty(registerRequest.getEmail()) ? "" : registerRequest.getEmail())
                .address(Empty.isEmpty(registerRequest.getAddress()) ? "" : registerRequest.getAddress())
                .build();

        usersRepository.save(users);
        return null;
    }
}