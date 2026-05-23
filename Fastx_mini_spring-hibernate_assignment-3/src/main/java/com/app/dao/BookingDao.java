package com.app.dao;


import com.app.model.Booking;

import java.util.List;

public interface BookingDao {
    void addBooking(Booking booking, String username);

    public Booking getById(int bookingId, String username);

    void deleteBooking(int id, String username);

    List<Booking> findAllBookings(String username);

    void update(Booking booking);

    List<Booking> getAll();

    void delete(int id);
}
