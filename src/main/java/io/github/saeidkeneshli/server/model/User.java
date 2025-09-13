package io.github.saeidkeneshli.server.model;

import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
}