package com.objectfrontier.training.hibernate.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.objectfrontier.training.hibernate.model.Address;
import com.objectfrontier.training.hibernate.model.Person;
import com.objectfrontier.training.hibernate.util.AppException;
import com.objectfrontier.training.hibernate.util.ErrorCode;

@Service
public class PersonService {

    @Autowired
    private AddressService addrService;

    private List<ErrorCode> errorList;

    SessionFactory factory = new Configuration()
            .configure("/hibernate.cfg.xml")
            .addAnnotatedClass(Person.class)
            .buildSessionFactory();


    private Session getNewSession() {

        Session session = factory.getCurrentSession();
        return session;
    }

    private void destroySession(Session session) {

        session.close();
    }

    public int create(Person addPerson) {

        Session session = getNewSession();
        int id = 0;
        try {
            List<ErrorCode> validationResult = fieldValidation(addPerson);
            if(validationResult.size() == 0) {
                session.beginTransaction();
                Address createdAddress = addrService.create(session, addPerson.getAddress());
                id = (int) session.save(addPerson);
                session.getTransaction().commit();
            } else {
                throw new AppException(validationResult);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3, e);
        } finally {
            destroySession(session);
        }
        return id;
    }

    public Person read(long id) {

        Person readPerson = new Person();
        Session session = getNewSession();
        try {
            session.beginTransaction();
            readPerson = session.get(Person.class, id);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            destroySession(session);
        }
        return readPerson;
    }

    public List<Person> readAll() {

        Session session = getNewSession();
        session.beginTransaction();
        List<Person> personList = session.createQuery("from person").getResultList();
        for (Person person : personList) {
            //            System.out.println(person);
        }
        destroySession(session);
        return personList;
    }

    public Person update(Person updatedPerson) {

        Session session = getNewSession();
        try{
            int rowsAffected = addrService.update(session, updatedPerson.getAddress());
            if(rowsAffected == 1) {
                int a = session.createQuery("update person set fname = 'ram' where id=" + updatedPerson.getId() ).executeUpdate();
                session.getTransaction().commit();
                if(a != 1) {
                    throw new AppException(ErrorCode.PERSON_NOT_FOUND);
                }
            }
        } catch(Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3);
        }finally {
            session.getTransaction().commit();
            destroySession(session);
        }

        return updatedPerson;
    }

    public int deletePerson(long person_id, long address_id) throws Exception {

        int rowsAffected;
        Session session = getNewSession();
        try {
            session.beginTransaction();
            int personTableRowsAffected = session.createQuery("delete from person where id=" + person_id).executeUpdate();

            if(personTableRowsAffected == 1) {
                rowsAffected = addrService.delete(address_id, session);
            } else {
                throw new AppException(ErrorCode.PERSON_NOT_FOUND); }
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3);
        } finally {
            session.getTransaction().commit();

            destroySession(session);
        }
        return rowsAffected;
    }


    public void search(Connection connection,String searchString, String[] column) throws SQLException {

        List<Address> addressDetails = new ArrayList<>();
        StringBuilder query = new StringBuilder().append("SELECT * FROM service_address ")
                .append("WHERE                         ");

        for (int i = 0; i < column.length; i++) {

            if (i > 0) {
                query.append(" OR ");
            }

            switch (column[i]) {

            case "street" :
                query.append(" street LIKE ? ");
                break;
            case "city" :
                query.append(" city LIKE ? ");
                break;
            case "postalCode" :
                query.append(" postal_code LIKE ? ");
                break;
            }
        }

        if (column.length == 0) {

            query.append("street        LIKE ? ")
            .append("    OR               ")
            .append("  city        LIKE ? ")
            .append("    OR               ")
            .append("postal_code   LIKE ? ");
        }
        PreparedStatement searchQuery = connection.prepareStatement(query.toString());

        if (column.length > 0) {
            for (int i = 0; i < column.length; i++) {
                searchQuery.setString(i + 1, "%" + searchString + "%");
            }
        } else {
            searchQuery.setString(1, "%" + searchString + "%");
            searchQuery.setString(2, "%" + searchString + "%");
            searchQuery.setString(3, "%" + searchString + "%");
        }

        ResultSet result = searchQuery.executeQuery();

        while (result.next()) {

            Address retrievedAddress = new Address();
            retrievedAddress.setId(result.getLong("id"));
            retrievedAddress.setStreet(result.getString("street"));
            retrievedAddress.setCity(result.getString("city"));
            retrievedAddress.setPostal_code(result.getInt("postal_code"));
            addressDetails.add(retrievedAddress);
        }
        for (Address address : addressDetails) {
            System.out.println(address.getId() + " " + address.getStreet() + " " + address.getCity() + " " + address.getPostal_code());
        }
        //            return true;
    }

    //    private List<ErrorCode> personValidation(Connection connection, Person addPerson) throws Exception {
    //
    //        errorList  = new ArrayList();
    //
    //        List<ErrorCode> errorList = fieldValidation(addPerson);
    //
    //        if(errorList.isEmpty()) {
    //            PersonService persService = new PersonService();
    //            List<Person> personList = persService.readAll(session);
    //            for(Person emailList : personList) {
    //                if(emailList.getEmail().equals(addPerson.getEmail())) {
    //                    errorList.add(ErrorCode.EMAIL_ALREADY_EXISTS);
    //                }
    //            }
    //            for(Person emailList : personList) {
    //                if(emailList.getFirstName().concat(emailList.getLastName()).equalsIgnoreCase(addPerson.getFirstName().concat(addPerson.getLastName()))) {
    //                    errorList.add(ErrorCode.PERSON_ALREADY_EXISTS);
    //                }
    //            }
    //        }
    //
    //        if(!errorList.isEmpty()) {
    //            throw new AppException(errorList);
    //        } else {
    //            return errorList;
    //        }
    //    }

    private List<ErrorCode> fieldValidation(Person addPerson) {

        errorList = addrService.addressValidation(addPerson.getAddress());
        if(addPerson.getFirstName().equalsIgnoreCase(addPerson.getLastName())) {
            errorList.add(ErrorCode.NAME_NOT_SAME);
        } if ((addPerson.getFirstName() == null) || (addPerson.getFirstName().trim().length() <= 1)) {
            errorList.add(ErrorCode.FIRST_NAME_NOT_NULL);
        } if ((addPerson.getLastName() == null) || (addPerson.getLastName().trim().length() < 1 )) {
            errorList.add(ErrorCode.LAST_NAME_NOT_NULL);
        } if ((addPerson.getEmail() == null) || (addPerson.getEmail().trim().length() <= 5)) {
            errorList.add(ErrorCode.EMAIL_NOT_NULL);
        }

        if(errorList.isEmpty()) {
            return errorList;
        } else {
            throw new AppException(errorList);
        }
    }

}

