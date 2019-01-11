package com.objectfrontier.training.java.service.service;

import static com.objectfrontier.training.java.service.service.Constants.BIRTH_DATE;
import static com.objectfrontier.training.java.service.service.Constants.CREATED_DATE;
import static com.objectfrontier.training.java.service.service.Constants.EMAIL;
import static com.objectfrontier.training.java.service.service.Constants.FULL_NAME;
import static com.objectfrontier.training.java.service.service.Constants.ID;
import static com.objectfrontier.training.java.service.service.Constants.LAST_NAME;
import static com.objectfrontier.training.java.service.service.Statements.CREATE_PERSON;
import static com.objectfrontier.training.java.service.service.Statements.DELETE_PERSON;
import static com.objectfrontier.training.java.service.service.Statements.READ_ALL_PERSON;
import static com.objectfrontier.training.java.service.service.Statements.READ_PERSON;
import static com.objectfrontier.training.java.service.service.Statements.UPDATE_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.test.AppException;

public class PersonService {

    List<ErrorCode> errorList;

    public Person create(Connection connection, Person addPerson) throws Exception {


        String[] birthDay = addPerson.getBirth_date().split("-");
        String bDate = birthDay[2] + "-" + birthDay[1] + "-" + birthDay[0];
        List<ErrorCode> validationDetails = personValidation(connection, addPerson);
        try {
            PreparedStatement ps = connection.prepareStatement(CREATE_PERSON, Statement.RETURN_GENERATED_KEYS);
                if(validationDetails.isEmpty()) {
                    AddressService adServe = new AddressService();
                    Address addrId = adServe.create(connection, addPerson.getAddress());
                    ps.setString(1, addPerson.getFirstName());
                    ps.setString(2, addPerson.getLastName());
                    ps.setString(3, addPerson.getEmail());
                    ps.setLong(4, addrId.getId());
                    ps.setString(5, bDate);
                    ps.setTimestamp(6, Timestamp.from(Instant.now()));
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    int personId = rs.getInt(1);
                    addPerson.setId(personId);
                }
            } catch (AppException e) {
                System.out.println(e.getErrorList());
                throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3, e);
            }
        return addPerson;
    }


        public Person read(Connection conn, long id, String choice) throws Exception {

            Person readPerson = new Person();
            try {
            PreparedStatement ps = conn.prepareStatement(READ_PERSON);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                if(choice.equalsIgnoreCase("n")) {
                    readPerson.setId(rs.getLong(ID));
                    readPerson.setFirstName(rs.getString(FULL_NAME));
                    readPerson.setLastName(rs.getString(LAST_NAME));
                    readPerson.setEmail(rs.getString(EMAIL));
                    readPerson.setBirth_date(rs.getString(BIRTH_DATE));
                    readPerson.setCreated_date(rs.getTimestamp(CREATED_DATE));
                    Address address = new Address();
                    address.setId(rs.getLong("address_id"));
                    readPerson.setAddress(address);
                } else {
                    readPerson.setId(rs.getLong(ID));
                    readPerson.setFirstName(rs.getString(FULL_NAME));
                    readPerson.setLastName(rs.getString(LAST_NAME));
                    readPerson.setEmail(rs.getString(EMAIL));
                    readPerson.setBirth_date(rs.getString(BIRTH_DATE));
                    readPerson.setCreated_date(rs.getTimestamp(CREATED_DATE));
                    Address address = new Address();
                    address.setId(rs.getLong("address_id"));
                    AddressService newAddress = new AddressService();
                    address = newAddress.read(conn, address);
                    readPerson.setAddress(address);
                }
            } else {
                throw new AppException(ErrorCode.PERSON_NOT_FOUND);
            }
            }catch (SQLException e) {
                throw new AppException(ErrorCode.CITY_NOT_NULL);
            }
        return readPerson;
}

        public List<Person> readAll(Connection conn) throws Exception {

            PreparedStatement ps = conn.prepareStatement(READ_ALL_PERSON);
            ResultSet rs = ps.executeQuery();

            List<Person> listOfPersons = new ArrayList<>();
            while(rs.next()) {
                Address forID = new Address();
                Person personList = new Person();
                personList.setAddress(forID);
                forID.setId(rs.getLong(1));
                personList.setId(rs.getLong(5));
                personList.setFirstName(rs.getString(6));
                personList.setLastName(rs.getString(7));
                personList.setEmail(rs.getString(8));
                personList.setBirth_date(rs.getString(10));
                personList.setCreated_date(rs.getTimestamp(11));
                listOfPersons.add(personList);
            }

            return listOfPersons;
        }

        public Person update(Connection conn, Person updatedPerson) throws Exception {

            String[] birthDay = updatedPerson.getBirth_date().split("-");
            String bDate = birthDay[2] + "-" + birthDay[1] + "-" + birthDay[0];
            try{
                AddressService address =  new AddressService();
                address.update(conn, updatedPerson.getAddress());

                List<ErrorCode> validationDetails = personValidation(conn, updatedPerson);
                if(validationDetails.isEmpty()) {
                    PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY);
                    ps.setString(1, updatedPerson.getFirstName());
                    ps.setString(2, updatedPerson.getLastName());
                    ps.setString(3, updatedPerson.getEmail());
                    ps.setString(4, bDate);
                    ps.setLong(5, updatedPerson.getId());
                    int rs = ps.executeUpdate();
                    if(rs != 1) {
                        throw new AppException(ErrorCode.PERSON_NOT_FOUND);
                    }
                }
            } catch(Exception e) {
                throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3);
            }

            return updatedPerson;
        }

        public int deletePerson(Connection conn, long person_id, long address_id) throws Exception {

            List<ErrorCode> exceptionList = new ArrayList<>();
            int personTable_RowsAffected = 0;
            try {
                PreparedStatement ps = conn.prepareStatement(DELETE_PERSON);
                ps.setLong(1, person_id);
                personTable_RowsAffected = ps.executeUpdate();
                if(personTable_RowsAffected == 1) {
                    AddressService delAddress = new AddressService();
                    int rowsAffected = delAddress.delete(conn, address_id);
                } else {
                    throw new AppException(ErrorCode.PERSON_NOT_FOUND);
                }
            } catch(AppException e) {
                throw new AppException(ErrorCode.PERSON_NOT_FOUND);
        }
            return personTable_RowsAffected;
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

        private List<ErrorCode> personValidation(Connection connection, Person addPerson) throws Exception {

            errorList  = new ArrayList();

            List<ErrorCode> errorList = fieldValidation(addPerson);

            if(errorList.isEmpty()) {
                PersonService ps1 = new PersonService();
                List<Person> personList = ps1.readAll(connection);
                for(Person emailList : personList) {
                    if(emailList.getEmail().equals(addPerson.getEmail())) {
                        errorList.add(ErrorCode.EMAIL_ALREADY_EXISTS);
                    }
                }
                for(Person emailList : personList) {
                    if(emailList.getFirstName().concat(emailList.getLastName()).equalsIgnoreCase(addPerson.getFirstName().concat(addPerson.getLastName()))) {
                        errorList.add(ErrorCode.PERSON_ALREADY_EXISTS);
                    }
                }
            }

            if(!errorList.isEmpty()) {
                throw new AppException(errorList);
            } else {
                return errorList;
            }
        }

        private List<ErrorCode> fieldValidation(Person addPerson) {

            AddressService addrService = new AddressService();
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

