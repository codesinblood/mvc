package com.objectfrontier.training.java.service.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ErrorCode;
import com.objectfrontier.training.java.service.util.Statement;

public class AuthenticationService {

    public Person validation(Connection con, Person person) throws SQLException {

        try {
            PreparedStatement ps = con.prepareStatement(Statement.LOGIN_VALIDATION);
            ps.setString(1, person.getEmail());
            ps.setString(2, person.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return person;
            } else {
                throw new AppException(ErrorCode.PERSON_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L3);
        }
    }
}
