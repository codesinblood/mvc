package com.objectfrontier.training.java.service.service;

import static com.objectfrontier.training.java.service.util.Constant.CITY;
import static com.objectfrontier.training.java.service.util.Constant.POSTAL_CODE;
import static com.objectfrontier.training.java.service.util.Constant.STREET;
import static com.objectfrontier.training.java.service.util.Statement.CREATE_ADDRESS;
import static com.objectfrontier.training.java.service.util.Statement.DELETE_ADDRESS;
import static com.objectfrontier.training.java.service.util.Statement.READ_ADDRESS;
import static com.objectfrontier.training.java.service.util.Statement.READ_ALL_PERSON;
import static com.objectfrontier.training.java.service.util.Statement.UPDATE_ADDRESS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ErrorCode;

public class AddressService {

    public Address create(Connection connection, Address addAddress) throws SQLException {

        List<ErrorCode> errorList;

        PreparedStatement ps = connection.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
        errorList = addressValidation(addAddress);
        if(errorList.isEmpty()) {
            ps.setString(1, addAddress.getStreet());
            ps.setString(2, addAddress.getCity());
            ps.setInt(3, addAddress.getPostal_code());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            addAddress.setId(rs.getLong(1));
        }
        return addAddress;
    }


    public boolean update(Connection conn, Address newAddress) throws Exception {

        List<ErrorCode> errorList;
        PreparedStatement ps = conn.prepareStatement(UPDATE_ADDRESS);
        errorList = addressValidation(newAddress);
        if(errorList.isEmpty()) {
            ps.setString(1, newAddress.getCity());
            ps.setString(2, newAddress.getStreet());
            ps.setInt(3, newAddress.getPostal_code());
            ps.setLong(4, newAddress.getId());
            int rs = ps.executeUpdate();
            if(rs != 0) {
                return true;
            } else {
                throw new AppException(ErrorCode.ID_NOT_FOUND);
            }
        }
    else {
            return false;
        }
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

    public int delete(Connection conn, long id) throws SQLException {

        int rowsAffected;
        try {
            PreparedStatement ps = conn.prepareStatement(DELETE_ADDRESS);
            ps.setLong(1, id);
            rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Address Deleted");
            } else {
                throw new AppException(ErrorCode.PERSON_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3);
        }
        return rowsAffected;
    }

}
