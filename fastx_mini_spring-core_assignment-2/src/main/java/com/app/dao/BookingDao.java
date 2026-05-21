package com.app.dao;

import com.app.exception.ResourceNotFoundException;
import com.app.model.Booking;

import java.util.List;

public interface BookingDao {
    void insertBooking(Booking booking);
    void deleteBooking(int id) throws ResourceNotFoundException;
    Booking getBookingById(int id) throws ResourceNotFoundException;
    List<Booking> getAllBookings();
    void updateBooking(Booking booking) throws ResourceNotFoundException;
}
