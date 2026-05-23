package com.app.daoImpl;

import com.app.dao.ScheduleDao;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ScheduleDaoImpl implements ScheduleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Schedule getById(int scheduleId) throws ResourceNotFoundException {

        Schedule schedule = em.find(Schedule.class, scheduleId);
        if(schedule == null)
            throw new ResourceNotFoundException("Schedule not found...");
        return schedule;
    }

    @Override
    public void add(Schedule schedule) {
        em.persist(schedule);
        System.out.println("Schedule added successfully...");
    }
}
