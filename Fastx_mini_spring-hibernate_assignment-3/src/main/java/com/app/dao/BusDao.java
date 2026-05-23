package com.app.dao;

import com.app.model.Bus;

public interface BusDao {
    void addBus(Bus bus);
    Bus getBusById(int id);
}
