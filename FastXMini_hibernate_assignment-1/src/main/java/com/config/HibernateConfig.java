package com.config;

import com.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/fastxmini?createDatabaseIfNotExist=true");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "2022pecec218");
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

            configuration.setProperty("hibernet.dialect", "org.hibernate.dialect.MySQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            configuration.addAnnotatedClass(Booking.class);
            configuration.addAnnotatedClass(Passenger.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Schedule.class);
            configuration.addAnnotatedClass(Bus.class);

            return configuration.buildSessionFactory();
        }
        return sessionFactory;
    }
}
