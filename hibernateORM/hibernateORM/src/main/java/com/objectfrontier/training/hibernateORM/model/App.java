package com.objectfrontier.training.hibernateORM.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class App {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(Address.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        deletePerson( factory,  session);
    }

    private static void readAddress(SessionFactory factory, Session session) {

        try {
            Address p = new Address();
            session.beginTransaction();
            p = session.get(Address.class, 15l);
            System.out.println(p);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

    private static void createAddress(SessionFactory factory, Session session) {

        try {
            Address p = new Address("voc","voc",60000);
            long id = 0;
            session.beginTransaction();
//            id = (long) session.save(p);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

    private static void readPerson(SessionFactory factory, Session session) {

        try {
            Person p = new Person();
            session.beginTransaction();
            long id = 237;
            p = session.get(Person.class, id);
            System.out.println(p);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

    private static void deletePerson(SessionFactory factory, Session session) {

        try {
            Person p = new Person();
            session.beginTransaction();
            long id = 237;
            int a = session.createQuery("delete from Person where id=" + id).executeUpdate();
            System.out.println(a);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

}
