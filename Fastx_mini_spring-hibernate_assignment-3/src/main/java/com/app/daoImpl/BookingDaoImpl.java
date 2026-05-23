package com.app.daoImpl;

import com.app.dao.BookingDao;
import com.app.dao.PassengerDao;
import com.app.enums.BookingStatus;
import com.app.exception.InvalidOwnershipException;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Booking;
import com.app.model.Passenger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
public class BookingDaoImpl implements BookingDao {

    @PersistenceContext
    private EntityManager em;

    private PassengerDao passengerDao;

    @Autowired
    public void setPassengerDao(PassengerDao passengerDao){

        this.passengerDao = passengerDao;
    }

    @Override
    public void addBooking(Booking booking, String username) {
        Passenger passenger = passengerDao.getByUsername(username);
        booking.setPassenger(passenger);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDate.now());
        em.persist(booking);
        System.out.println("Booking added successfully...");
    }

    @Override
    public Booking getById(int bookingId, String username) {

        Booking booking = em.find(Booking.class, bookingId);
        if(booking == null)
            throw new ResourceNotFoundException("Booking not found...");
        if(!(booking.getPassenger().getUser().getUsername().equals(username)))
            throw new InvalidOwnershipException("You do not own this booking...");
        return booking;
    }

    @Override
    public void deleteBooking(int id, String username)  {
        Booking booking = getById(id, username);

        em.remove(booking);
        System.out.println("Booking successfully cancelled...");
    }

    @Override
    public List<Booking> findAllBookings(String username) {
        String sql = "select b from Booking b where b.passenger.user.username=:username";
        TypedQuery<Booking> query = em.createQuery(sql, Booking.class);
        query.setParameter("username", username);
        return query.getResultList();

    }

    @Override
    public void update(Booking booking) {
        em.merge(booking);
        System.out.println("Booking updated successfully...");
    }

    @Override
    public List<Booking> getAll() {
        return em.createQuery("select b from Booking b", Booking.class).getResultList();

    }

    @Override
    public void delete(int id) {
        Booking booking = em.find(Booking.class, id);
        if(booking == null)
            throw new ResourceNotFoundException("Invalid id...");
        em.remove(booking);
        System.out.println("Booking deleted successfully...");
    }
}
