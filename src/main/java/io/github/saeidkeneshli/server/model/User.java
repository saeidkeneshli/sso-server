package io.github.saeidkeneshli.server.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "USERNAME", nullable = false, columnDefinition = "VARCHAR2(60)")
    private String username;

    @Column(name = "PASSWORD", nullable = false, columnDefinition = "VARCHAR2(60)")
    private String password;

    @Column(name = "PHONE_NUMBER", nullable = false, columnDefinition = "VARCHAR2(13)")
    private String phoneNumber;

    @Column(name = "EMAIL", columnDefinition = "VARCHAR2(60)")
    private String email;

    @Column(name = "ADDRESS", columnDefinition = "VARCHAR2(255)")
    private String address;

}