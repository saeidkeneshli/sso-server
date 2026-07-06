package io.github.saeidkeneshli.server.dto.register;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;

    private String phoneNumber;
    private String email;
    private String address;

    //TODO: add security questions
}