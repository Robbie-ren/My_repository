package com.service;

import com.model.Passenger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PassengerService {
    private final Session session;

    public PassengerService(Session session){
        this.session = session;
    }

    public Passenger getByUsername(String username) {
        Transaction tx = session.beginTransaction();
        Passenger passenger = session.createQuery("select p from Passenger p where p.user.username = :username", Passenger.class)
                        .setParameter("username", username)
                                .getSingleResult();
        tx.commit();
        return passenger;
    }
}
