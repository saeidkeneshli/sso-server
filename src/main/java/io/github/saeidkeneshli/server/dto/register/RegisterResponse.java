package io.github.saeidkeneshli.server.dto.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private boolean accepted;
    private String username;
    private String password;

    private String phoneNumber;
    private String address;

    //TODO: add security questions
}