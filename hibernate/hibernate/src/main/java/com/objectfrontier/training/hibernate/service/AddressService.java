package com.objectfrontier.training.hibernate.service;

import static com.objectfrontier.training.hibernate.util.Constant.CITY;
import static com.objectfrontier.training.hibernate.util.Constant.POSTAL_CODE;
import static com.objectfrontier.training.hibernate.util.Constant.STREET;
import static com.objectfrontier.training.hibernate.util.Statement.READ_ADDRESS;
import static com.objectfrontier.training.hibernate.util.Statement.READ_ALL_PERSON;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.objectfrontier.training.hibernate.model.Address;
import com.objectfrontier.training.hibernate.util.AppException;
import com.objectfrontier.training.hibernate.util.ErrorCode;

@Service
public class AddressService {

//    SessionFactory factory = new Configuration()
//            .configure("/hibernate.cfg.xml")
//            .addAnnotatedClass(Address.class)
//            .buildSessionFactory();
//
//    Session session = factory.getCurrentSession();

    public Address create(Session session, Address addAddress) throws SQLException {

//        List<ErrorCode> errorList;
//
//        PreparedStatement ps = connection.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
//        errorList = addressValidation(addAddress);
//        if(errorList.isEmpty()) {
//            ps.setString(1, addAddress.getStreet());
//            ps.setString(2, addAddress.getCity());
//            ps.setInt(3, addAddress.getPostal_code());
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            rs.next();
//            addAddress.setId(rs.getLong(1));
//        }

        session.save(addAddress);
        System.out.println(addAddress.getId());
        return addAddress;
    }

    public int update(Session session, Address newAddress) {

        session.beginTransaction();
        int a = session.createQuery("update address set street = 'ram' where id=" + newAddress.getId() ).executeUpdate();
        return a;
    }

    public List<ErrorCode> addressValidation(Address newAddress) {

        List<ErrorCode> errorList = new ArrayList<>();

        if((newAddress.getCity() == null) || (newAddress.getCity().trim().length() <= 1)) {
            errorList.add(ErrorCode.CITY_NOT_NULL);
        } if((newAddress.getStreet() == null) || (newAddress.getStreet().trim().length() <= 1)) {
            errorList.add(ErrorCode.STREET_NOT_NULL);
        } if (newAddress.getPostal_code() < 0) {
            errorList.add(ErrorCode.PINCODE_NOT_NULL);
        }

        return errorList;
    }

    public Address read(Connection conn, Address readAddress) throws Exception {

        PreparedStatement ps = conn.prepareStatement(READ_ADDRESS);
        ps.setLong(1, readAddress.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            readAddress.setId(rs.getLong(1));
            readAddress.setCity(rs.getString(CITY));
            readAddress.setStreet(rs.getString(STREET));
            readAddress.setPostal_code(rs.getInt(POSTAL_CODE));
        }
        return readAddress;
    }

    public List<Address> readAll(Connection conn) throws Exception {

        PreparedStatement ps = conn.prepareStatement(READ_ALL_PERSON);
        ResultSet rs = ps.executeQuery();

        List<Address> listOfAddress = new ArrayList<>();
        while(rs.next()) {
            Address forID = new Address();
            forID.setId(rs.getLong(1));
            forID.setStreet(rs.getString(STREET));
            forID.setCity(rs.getString(CITY));
            forID.setPostal_code(rs.getInt(POSTAL_CODE));
            listOfAddress.add(forID);
        }
        return listOfAddress;
    }

    public int delete(long id, Session session) throws SQLException {

        int rowsAffected;
        try {

            rowsAffected = session.createQuery("delete from address where id=" + id).executeUpdate();
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3);
        }
        return rowsAffected;
    }

}
