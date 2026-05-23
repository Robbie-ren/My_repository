package com.app.daoImpl;

import com.app.dao.BusDao;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Bus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class BusDaoImpl implements BusDao {

    @PersistenceContext
    private EntityManager em;

    public Bus getBusById(int id) {
        Bus bus = em.find(Bus.class, id);
        if(bus==null)
            throw new ResourceNotFoundException("Invalid bus id...");
        return bus;
    }

    @Override
    public void addBus(Bus bus) {
        em.persist(bus);
        System.out.println("Bus added successfully...");
    }
}
