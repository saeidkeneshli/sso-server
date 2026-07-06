package io.github.saeidkeneshli.server.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtil {

    private static final PasswordEncoder PASSWORD_ENCODER =
            new BCryptPasswordEncoder();

    private PasswordUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String hash(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank.");
        }
        return PASSWORD_ENCODER.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return PASSWORD_ENCODER.matches(rawPassword, hashedPassword);
    }
}