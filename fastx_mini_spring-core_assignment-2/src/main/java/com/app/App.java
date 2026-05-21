package com.app;

import com.app.config.AppConfig;
import com.app.dao.BookingDao;
import com.app.daoImpl.BookingDaoImpl;
import com.app.enums.BookingStatus;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Booking;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        BookingDao bookingDao = context.getBean(BookingDaoImpl.class);
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to FastX!");
        System.out.println("----Menu----");
        while(true){
            System.out.println("1. Add booking");
            System.out.println("2. Delete booking");
            System.out.println("3. View booking by id");
            System.out.println("4. View all bookings");
            System.out.println("5. Update booking");
            System.out.println("0. Exit");
            System.out.println("Enter an option from menu: ");
            int op = sc.nextInt();
            if(op == 0)
                break;
            switch(op){
                case 1:
                    Booking booking = new Booking();
                    System.out.println("Enter the number of seats: ");
                    int seatCount = sc.nextInt();
                    System.out.println("Enter the schedule id: ");
                    int scheduleId = sc.nextInt();
                    booking.setSeatCount(seatCount);
                    booking.setBookingDate(LocalDate.now());
                    booking.setBookingStatus(BookingStatus.CONFIRMED);
                    booking.setTotalAmount(50 * seatCount);
                    booking.setScheduleId(scheduleId);
                    bookingDao.insertBooking(booking);
                    break;
                case 2:
                    try {
                        System.out.println("Enter id to delete: ");
                        int id = sc.nextInt();
                        bookingDao.deleteBooking(id);
                    }catch(ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter the id to view booking: ");
                    int id = sc.nextInt();
                    try {
                        System.out.println(bookingDao.getBookingById(id));
                    }catch(EmptyResultDataAccessException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    bookingDao.getAllBookings().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Enter id to update: ");
                    id = sc.nextInt();
                    System.out.println("----Existing booking----");
                    booking = bookingDao.getBookingById(id);
                    System.out.println(booking);
                    System.out.println("Enter seat count to update");
                    seatCount = sc.nextInt();
                    booking.setSeatCount(seatCount);
                    bookingDao.updateBooking(booking);
                    break;
                default:
                    break;
            }
        }
        sc.close();
        context.close();
    }

}
