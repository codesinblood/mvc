package com.objectfrontier.training.hibernate.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.objectfrontier.training.hibernate.model.Address;

public class hibernateDemo {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(Address.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

    updatePerson(factory, session, 250l);
    }

//    private static void createPerson(SessionFactory factory, Session session) {
//
//        try {
//            Address address = new Address("ofs", "csir", 600113);
//            Address createdAddress = createAddress(factory, session, address);
//            Person p = new Person("siva", "arun", "siva1@arun", createdAddress, "1997-06-06", "password1", true);
//            p.setCreated_date(Timestamp.from(Instant.now()));
//            session.save(p);
//            System.out.println(p.getId());
//            session.getTransaction().commit();
//        } finally {
//            session.close();
//            factory.close();
//        }
//    }

//    private static void readperson(SessionFactory factory, Session session) {
//
//        try {
//            session.beginTransaction();
//            List<Person> personList = session.createQuery("from person").getResultList();
//            for (Person person : personList) {
//                System.out.println(person);
//            }
////            return address;
//        } finally {
//            session.close();
//          factory.close();
//        }
//    }

    private static void deletePerson(SessionFactory factory, Session session) {

                try {
                    session.beginTransaction();
                    int a = session.createQuery("delete from person where id=240").executeUpdate();
                    System.out.println(a);
                    session.getTransaction().commit();
                } finally {
                    session.close();
                    factory.close();
                }
            }

    private static void updatePerson(SessionFactory factory, Session session, long id) {

        try {
            session.beginTransaction();
            int a = session.createQuery("update person set fname = 'ram' where id=" + id).executeUpdate();
            System.out.println(a);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

}
