package com.service;

import com.enums.BookingStatus;
import com.exception.InvalidOwnershipException;
import com.exception.ResourceNotFoundException;
import com.model.Booking;
import com.model.Passenger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BookingService {
    private final Session session;
    private final PassengerService passengerService;

    public BookingService(Session session){

        this.session = session;
        passengerService = new PassengerService(session);
    }


    public List<Booking> getAllBookings() {
        Transaction tx = session.beginTransaction();
        List<Booking> list = session.createQuery("from Booking", Booking.class).list();
        tx.commit();
        return list;
    }

    public Booking getById(int id, String username) {
       Transaction tx = session.beginTransaction();
       Booking booking = session.find(Booking.class, id);
       tx.commit();
        if(booking == null)
            throw new ResourceNotFoundException("Booking not found");
        Passenger passenger = passengerService.getByUsername(username);
        if(booking.getPassenger().getId() != passenger.getId())
            throw new InvalidOwnershipException("You do not own this booking...");
       return booking;
    }

    public void addBooking(Booking booking, String username) {
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        Passenger passenger = passengerService.getByUsername(username);

        booking.setPassenger(passenger);

        Transaction tx = session.beginTransaction();
        session.persist(booking);
        tx.commit();
    }

    public void deleteBooking(int bookingId, String username) {
        Transaction tx = session.beginTransaction();
        Booking booking = session.find(Booking.class, bookingId);
        tx.commit();
        if(booking == null){
            throw new ResourceNotFoundException("Booking id invalid");
        }

        Passenger passenger = passengerService.getByUsername(username);

        if(booking.getPassenger().getId() != passenger.getId()){
            throw new InvalidOwnershipException("Invalid ownership. Cancellation failed");
        }else {
            tx = session.beginTransaction();
            session.remove(booking);
            tx.commit();
        }
    }

    public List<Booking> getByUsername(String username) {
        Transaction tx = session.beginTransaction();
        List<Booking> bookings = session.createQuery("select b from Booking b where b.passenger.user.username=:username", Booking.class).
                setParameter("username", username).
                list();
        tx.commit();
        if(bookings.isEmpty())
            throw new ResourceNotFoundException("No bookings found...");
        return bookings;
    }

    public void update(Booking booking) {
        Transaction tx = session.beginTransaction();
        session.persist(booking);
        tx.commit();

        System.out.println("Booking updated successfully...");
    }

    public void deleteBookingById(int id) {
        Transaction tx = session.beginTransaction();
        Booking booking = session.find(Booking.class, id);
        if(booking==null)
            throw new ResourceNotFoundException("Booking not found...");
        session.remove(booking);
        tx.commit();
        System.out.println("Booking deleted successfully...");
    }
}
