package com.service;

import com.exception.ResourceNotFoundException;
import com.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthService {
    private final Session session;

    public AuthService(Session session){
        this.session = session;
    }

    public User login(String username, String password) {
        Transaction tx = session.beginTransaction();
        User user = session.createQuery("from User where username=:username and password=:password", User.class)
                        .setParameter("username", username)
                                .setParameter("password", password)
                                        .getSingleResult();
        tx.commit();
        if(user == null)
            throw new ResourceNotFoundException("Invalid credentials");

        return user;
    }
}
