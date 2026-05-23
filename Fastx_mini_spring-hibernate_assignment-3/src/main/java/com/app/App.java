package com.app;

import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao.BookingDao;
import com.app.dao.BusDao;
import com.app.dao.ScheduleDao;
import com.app.daoImpl.AuthDaoImpl;
import com.app.exception.InvalidOwnershipException;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Booking;
import com.app.model.Bus;
import com.app.model.Schedule;
import com.app.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AuthDao authDao = context.getBean(AuthDaoImpl.class);
        BookingDao bookingDao = context.getBean(BookingDao.class);
        ScheduleDao scheduleDao = context.getBean(ScheduleDao.class);
        BusDao busDao = context.getBean((BusDao.class));

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to FastX!");
        System.out.println("-----FastX: LOGIN-----");
        System.out.println("Enter the username: ");
        String username = sc.next();
        System.out.println("Enter the password: ");
        String password = sc.next();
        try {
            User user = authDao.login(username, password);
            System.out.println("Welcome " + username);
            switch(user.getRole().toString()){
                case "PASSENGER":
                    while(true){
                        System.out.println("-----Passenger Menu-----");
                        System.out.println("1. Create Booking");
                        System.out.println("2. Cancel Booking");
                        System.out.println("3. View My Bookings");
                        System.out.println("4. Update booking");
                        System.out.println("0. Exit ");
                        int op = sc.nextInt();
                        if(op == 0)
                            break;
                        switch(op){
                            case 1:
                                System.out.println("Enter the schedule id: ");
                                int scheduleId = sc.nextInt();
                                System.out.println("Enter the number of seats: ");
                                int seatCount = sc.nextInt();
                                try {
                                    Schedule schedule = scheduleDao.getById(scheduleId);
                                    Booking booking = new Booking(schedule, seatCount);
                                    booking.setTotalAmount(schedule.getPrice() * seatCount);
                                    bookingDao.addBooking(booking, username);
                                }catch(ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                System.out.println("Enter booking id to delete...");
                                int id = sc.nextInt();
                                try {
                                    bookingDao.deleteBooking(id, username);
                                }catch(ResourceNotFoundException | InvalidOwnershipException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.println("-----All Bookings-----");
                                bookingDao.findAllBookings(username).forEach(System.out::println);
                                break;
                            case 4:
                                System.out.println("Enter the booking id to update: ");
                                id = sc.nextInt();
                                try {
                                    Booking booking = bookingDao.getById(id, username);
                                    System.out.println("-----Existing record-----");
                                    System.out.println(booking);
                                    System.out.println("Enter the seat count to be updated: ");
                                    seatCount = sc.nextInt();
                                    System.out.println("Enter the schedule id to be updated: ");
                                    scheduleId = sc.nextInt();
                                    Schedule schedule = scheduleDao.getById(scheduleId);
                                    booking.setSchedule(schedule);
                                    booking.setSeatCount(seatCount);
                                    booking.setTotalAmount(schedule.getPrice() * seatCount);
                                    bookingDao.update(booking);
                                }catch (ResourceNotFoundException | InvalidOwnershipException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case "BUS_OPERATOR":

                    while(true) {
                    System.out.println("----Bus operator menu----");
                    System.out.println("1. Add Bus");
                    System.out.println("2. Add Schedule");
                    System.out.println("0. Exit");
                    int op = sc.nextInt();
                    if(op == 0)
                        break;
                    switch(op){
                        case 1:
                            System.out.println("Enter the bus name: ");
                            sc.nextLine();
                            String name = sc.nextLine();
                            System.out.println("Enter the bus number: ");
                            String busNumber = sc.nextLine();
                            System.out.println("Enter the total number of seats: ");
                            int seats = sc.nextInt();
                            Bus bus = new Bus(name, busNumber, seats);
                            busDao.addBus(bus);
                            break;

                        case 2:
                            System.out.println("Enter the source city: ");
                            sc.nextLine();
                            String sCity = sc.nextLine();
                            System.out.println("Enter the Destination: ");
                            String dCity = sc.nextLine();
                            System.out.println("Enter departureDate: ");
                            String depDate = sc.nextLine();
                            LocalDate dep_Date = LocalDate.parse(depDate);
                            System.out.println("Enter the departure time: ");
                            String depTime = sc.nextLine();
                            LocalTime dep_time = LocalTime.parse(depTime);
                            System.out.println("Enter the price: ");
                            double price = sc.nextDouble();
                            System.out.println("Enter the bus id: ");
                            int id = sc.nextInt();
                            try {
                                bus = busDao.getBusById(id);
                                int availableSeats = bus.getTotalSeats();
                                Schedule schedule = new Schedule(sCity, dCity, dep_Date, dep_time, availableSeats, price, bus);
                                scheduleDao.add(schedule);
                            }catch (ResourceNotFoundException e){
                                System.out.println(e.getMessage());
                            }
                        default:
                            break;

                    }
                }
                    break;
                case "ADMIN":
                    while(true) {
                        System.out.println("----Admin menu-----");
                        System.out.println("1. View All Bookings");
                        System.out.println("2. Delete Booking");
                        System.out.println("0. Exit");
                        int op = sc.nextInt();
                        if(op == 0)
                            break;
                        switch(op) {
                            case 1:
                                bookingDao.getAll().forEach(System.out::println);
                                break;
                            case 2:
                                System.out.println("Enter booking id to delete: ");
                                int id = sc.nextInt();
                                try {
                                    bookingDao.delete(id);
                                }catch (ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }catch(NoResultException e){
            System.out.println("Invalid credentials...");
        }
        context.close();
    }
}
