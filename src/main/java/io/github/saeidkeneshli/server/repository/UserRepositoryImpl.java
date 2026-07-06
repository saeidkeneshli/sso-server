package io.github.saeidkeneshli.server.repository;

import io.github.saeidkeneshli.server.model.User;
import io.github.saeidkeneshli.server.util.Empty;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    public User findById(Long id) {
        if (Empty.isEmpty(id)) {
            System.out.println("Bad Request"); //todo: add exception
        }
        return em.find(User.class, id);
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = em.createQuery(
                        "SELECT u FROM USERS u WHERE u.username = :username",
                        User.class)
                .setParameter("username", username)
                .getResultList();

        return users.stream().findFirst();
    }
}
