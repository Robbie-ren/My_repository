package com.service;

import com.exception.ResourceNotFoundException;
import com.model.Schedule;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ScheduleService {

    private Session session;

    public ScheduleService(Session session) {
        this.session = session;
    }

    public Schedule getScheduleById(int scheduleId) {
        Transaction tx = session.beginTransaction();
        Schedule schedule = session.find(Schedule.class, scheduleId);
        tx.commit();
        if(schedule == null)
            throw new ResourceNotFoundException("Schedule not found...");
        return schedule;
    }
}
