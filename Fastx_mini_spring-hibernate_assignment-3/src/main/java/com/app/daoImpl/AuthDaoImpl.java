package com.app.daoImpl;

import com.app.dao.AuthDao;
import com.app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AuthDaoImpl implements AuthDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User login(String username, String password) {
        String sql ="select u from User u where username=:username and password=:password";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        return query.getSingleResult();
    }
}
