package io.github.saeidkeneshli.server.service;

import io.github.saeidkeneshli.server.dto.register.RegisterRequest;
import io.github.saeidkeneshli.server.dto.register.RegisterResponse;
import io.github.saeidkeneshli.server.model.User;
import io.github.saeidkeneshli.server.repository.UsersRepository;
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

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    @Override
    public RegisterResponse registerNewUser(RegisterRequest registerRequest) {
        if (usersRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            System.out.println("user already registered.");
            return null;
        }

        String hashedPassword = PasswordUtil.hash(registerRequest.getPassword());

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(hashedPassword)
                .phoneNumber(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .address(registerRequest.getAddress().isEmpty() ? "" : registerRequest.getAddress())
                .build();

        usersRepository.save(user);
        return null;
    }
}