package com.app.model;

import com.app.enums.BookingStatus;

import java.time.LocalDate;

public class Booking {
    private int id;
    private LocalDate bookingDate;
    private BookingStatus bookingStatus;
    private int scheduleId;
    private int seatCount;
    private double totalAmount;


    public Booking() {
    }

    public Booking(int id, LocalDate bookingDate, BookingStatus bookingStatus, int scheduleId, int seatCount, double totalAmount) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.scheduleId = scheduleId;
        this.seatCount = seatCount;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingDate=" + bookingDate +
                ", bookingStatus=" + bookingStatus +
                ", scheduleId=" + scheduleId +
                ", seatCount=" + seatCount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
