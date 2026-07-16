package io.github.saeidkeneshli.server.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CredentialUtil {
    public record BasicCredentials(String username, String password) {}

    public static BasicCredentials decodeBasicAuth(String authorization) {
        if (authorization == null || !authorization.startsWith("Basic ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String encoded = authorization.substring(6);
        String decoded = new String(
                Base64.getDecoder().decode(encoded),
                StandardCharsets.UTF_8
        );

        String[] parts = decoded.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid Basic credentials");
        }

        return new BasicCredentials(parts[0], parts[1]);
    }
}
