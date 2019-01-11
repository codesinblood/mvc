package com.objectfrontier.training.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.objectfrontier.training.hibernate.model.Address;
import com.objectfrontier.training.hibernate.model.Person;


@EnableWebMvc
public class ApplicationConfig {

    @Bean
    private static SessionFactory buildSessionFactory() {

            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Address.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            return sessionFactory;
    }



}

