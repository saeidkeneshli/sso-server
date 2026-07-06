package io.github.saeidkeneshli.server.service;

import io.github.saeidkeneshli.server.dto.register.RegisterRequest;
import io.github.saeidkeneshli.server.dto.register.RegisterResponse;

public interface UserService {
    RegisterResponse registerNewUser(RegisterRequest registerRequest);
}