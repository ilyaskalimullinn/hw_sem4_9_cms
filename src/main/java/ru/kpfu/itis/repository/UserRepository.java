package ru.kpfu.itis.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.model.User;

import java.util.List;

@Component
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    public User findByUsername(String username) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE username = :username", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();
        if (users.isEmpty()) return null;
        return users.get(0);
    }

    @Transactional
    public void save(User user) {
        em.persist(user);
    }
}
