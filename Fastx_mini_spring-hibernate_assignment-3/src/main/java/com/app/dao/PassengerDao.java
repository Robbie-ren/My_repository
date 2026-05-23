package com.app.dao;

import com.app.model.Passenger;

public interface PassengerDao {
    Passenger getByUsername(String username);
}
