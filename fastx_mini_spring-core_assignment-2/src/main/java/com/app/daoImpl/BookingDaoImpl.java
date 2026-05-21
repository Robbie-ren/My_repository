package com.app.daoImpl;

import com.app.dao.BookingDao;
import com.app.enums.BookingStatus;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Booking;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingDaoImpl implements BookingDao {

    private JdbcTemplate jdbcTemplate;

    public BookingDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Booking> mapper(){
        return (rs, num) -> {
            return new Booking(rs.getInt("id"),
                            rs.getDate("bookingDate").toLocalDate(),
                            BookingStatus.valueOf(rs.getString("bookingStatus").toUpperCase()),
                            rs.getInt("scheduleId"),
                            rs.getInt("seatCount"),
                            rs.getDouble("totalAmount")
                            );
        };
    }

    @Override
    public void insertBooking(Booking booking) {
        String sql = "insert into booking (bookingDate, bookingStatus, seatCount, totalAmount, scheduleId) values (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                booking.getBookingDate(),
                booking.getBookingStatus().toString(),
                booking.getSeatCount(),
                booking.getTotalAmount(),
                booking.getScheduleId());
        System.out.println("Booking added...");
    }

    @Override
    public void deleteBooking(int id) throws ResourceNotFoundException {
        String sql = "delete from booking where id = ?";
        int num_of_rows = jdbcTemplate.update(sql, id);
        if(num_of_rows == 0)
            throw new ResourceNotFoundException("Invalid id...");
        System.out.println("Booking deleted...");
    }

    @Override
    public Booking getBookingById(int id) {
        String sql = "select * from booking where id = ?";
        return jdbcTemplate.queryForObject(sql, mapper(), id);
    }

    @Override
    public List<Booking> getAllBookings() {
        String sql = "select * from booking";
        return jdbcTemplate.query(sql, mapper());
    }

    @Override
    public void updateBooking(Booking booking) throws ResourceNotFoundException {
        String sql = "update booking set seatCount = ? where id = ?";
        jdbcTemplate.update(sql, booking.getSeatCount(), booking.getId());
        System.out.println("Booking updated...");
    }
}
