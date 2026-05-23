package com.app.daoImpl;

import com.app.dao.PassengerDao;
import com.app.model.Passenger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class PassengerDaoImpl implements PassengerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Passenger getByUsername(String username) {
        String sql = "select p from Passenger p where p.user.username=:username";
        TypedQuery<Passenger> query = em.createQuery(sql, Passenger.class);
        query.setParameter("username", username);

        return query.getSingleResult();
    }
}
