package com.controller;

import com.config.HibernateConfig;
import com.enums.BookingStatus;
import com.exception.InvalidOwnershipException;
import com.exception.ResourceNotFoundException;
import com.model.Booking;
import com.model.Schedule;
import com.model.User;
import com.service.AuthService;
import com.service.BookingService;
import com.service.ScheduleService;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Session session = HibernateConfig.getSessionFactory().openSession();

        Scanner sc = new Scanner(System.in);
        BookingService bookingService = new BookingService(session);
        AuthService authService = new AuthService(session);
        ScheduleService scheduleService = new ScheduleService(session);

        System.out.println("-----FastX: LOGIN------");
        System.out.println("Enter the username: ");
        String username = sc.next();
        System.out.println("Enter the password: ");
        String password = sc.next();

        try {
            User user = authService.login(username, password);

            switch (user.getRole().toString()){

                case "PASSENGER":
                    System.out.println("----Passenger menu----");
                    while(true) {
                        System.out.println("1. Create Booking");
                        System.out.println("2. Cancel Booking");
                        System.out.println("3. View My Bookings");
                        System.out.println("4. Update booking");
                        System.out.println("0. Exit ");
                        int op = sc.nextInt();
                        if (op == 0)
                            break;
                        switch (op) {
                            case 1:
                                Booking booking = new Booking();
                                System.out.println("Enter the schedule id: ");
                                int scheduleId = sc.nextInt();
                                try {
                                    Schedule schedule = scheduleService.getScheduleById(scheduleId);
                                    booking.setSchedule(schedule);
                                    System.out.println("Enter the seat count: ");
                                    int seatCount = sc.nextInt();
                                    booking.setSeatCount(seatCount);
                                    booking.setTotalAmount(seatCount * schedule.getPrice());
                                    bookingService.addBooking(booking, username);
                                    System.out.println("Booking is confirmed");
                                }catch(ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 2:
                                try {
                                    System.out.println("Enter the booking id to cancel: ");
                                    int bookingId = sc.nextInt();
                                    bookingService.deleteBooking(bookingId, username);
                                    System.out.println("Booking cancelled successfully");
                                } catch (InvalidOwnershipException | ResourceNotFoundException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                try {
                                    bookingService.getByUsername(username).forEach(System.out::println);
                                }catch (ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 4:
                                System.out.println("Enter the booking id to update: ");
                                int id = sc.nextInt();
                                try {
                                    booking = bookingService.getById(id, username);
                                    System.out.println("-----Existing record-----");
                                    System.out.println(booking);
                                    System.out.println("Enter the schedule id: ");
                                    scheduleId = sc.nextInt();
                                    System.out.println("Enter the seat count: ");
                                    int seatCount = sc.nextInt();
                                    booking.setSchedule(scheduleService.getScheduleById(scheduleId));
                                    booking.setSeatCount(seatCount);
                                    booking.setTotalAmount(seatCount * scheduleService.getScheduleById(scheduleId).getPrice());
                                    bookingService.update(booking);
                                }catch(InvalidOwnershipException | ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;

                            default:
                                System.out.println("invalid option. try again");
                                break;
                        }

                    }
                    break;
                    case "ADMIN":
                        System.out.println("----Admin menu-----");
                        while(true) {
                            System.out.println("1. View All Bookings");
                            System.out.println("2. Delete Booking");
                            System.out.println("3. Exit");

                            int op = sc.nextInt();
                            if (op == 0)
                                break;
                            switch (op){
                                case 1:
                                    bookingService.getAllBookings().forEach(System.out::println);
                                    break;
                                case 2:
                                    System.out.println("Enter the id of booking to delete: ");
                                    int id = sc.nextInt();
                                    try {
                                        bookingService.deleteBookingById(id);
                                    }catch(ResourceNotFoundException e){
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                            }
                        }
                        break;
                }

        }catch(NoResultException e){
            System.out.println("Invalid credentials");
        }
        sc.close();
        session.close();

    }


}
