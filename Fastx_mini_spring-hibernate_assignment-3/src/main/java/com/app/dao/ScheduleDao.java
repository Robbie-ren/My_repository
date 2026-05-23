package com.app.dao;

import com.app.exception.ResourceNotFoundException;
import com.app.model.Schedule;

public interface ScheduleDao {
    Schedule getById(int scheduleId) throws ResourceNotFoundException;

    void add(Schedule schedule);
}
