package io.github.saeidkeneshli.server.repository;

import io.github.saeidkeneshli.server.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    void save(User user);

    User findById(Long id);

    Optional<User> findByUsername(String username);
}
