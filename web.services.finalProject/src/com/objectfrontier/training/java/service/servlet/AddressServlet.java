package com.objectfrontier.training.java.service.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.service.AddressService;
import com.objectfrontier.training.java.service.service.PersonService;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ConnectionManager;
import com.objectfrontier.training.java.service.util.ErrorCode;
import com.objectfrontier.training.java.service.util.JsonConverter;

public class AddressServlet extends HttpServlet {

    Connection con;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/Json");
        PrintWriter out = res.getWriter();
        String id = req.getParameter("id");

        if(Objects.isNull(id)) {
            try {

                con = ConnectionManager.createConnection().getConnection();
                AddressService readAllAddress = new AddressService();
                List<Address> addressList = readAllAddress.readAll(con);
                for(Address list : addressList) {
                    out.write(list.toString() + "</br>");
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        } else {
            try {

                con = ConnectionManager.createConnection().getConnection();
                Address toRead = new Address();
                toRead.setId(Long.parseLong(id));
                Address readAddress = new AddressService().read(con, toRead);
                out.write(JsonConverter.toJson(readAddress));
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/json");
        BufferedReader reader = req.getReader();
        con = ConnectionManager.myThreadLocal.get();
        List<String> jsonLines = reader.lines().collect(Collectors.toList());
        String addressJson = String.join("", jsonLines);
        String id = req.getParameter("id");
        PrintWriter writer = res.getWriter();
        PrintWriter out = res.getWriter();
        Address input = JsonConverter.toObject(addressJson, Address.class);
        System.out.println(input.toString());
        AddressService addrService = new AddressService();
        Address address;

        if ("create".equalsIgnoreCase(req.getParameter("ops"))){
            try {
                address = addrService.create(con,input);
                System.out.println(address.toString());
                res.setStatus(HttpServletResponse.SC_OK);
                out.write(JsonConverter.toJson(address));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L1);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/Json");
        BufferedReader reader = req.getReader();
        List<String> jsonLines = reader.lines().collect(Collectors.toList());
        String addressJson = String.join("", jsonLines);
        String id = req.getParameter("id");
        PrintWriter writer = res.getWriter();

        if(Objects.isNull(id)) {
            try {
                con = ConnectionManager.createConnection().getConnection();
                Address input = JsonConverter.toObject(addressJson, Address.class);
                boolean person = new AddressService().update(con, input);
                writer.write(JsonConverter.toJson(person));
                con.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            try {
                con = ConnectionManager.createConnection().getConnection();
                Person input = JsonConverter.toObject(addressJson, Person.class);
                int person = new PersonService().deletePerson(con, input.getId(), input.getAddress().getId());
                writer.write(person);
                res.setStatus(HttpServletResponse.SC_OK);
                con.close();

            } catch (Exception e) {

                System.out.println("hi");
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                if(e instanceof AppException) {
                    writer.write(JsonConverter.toJson(((AppException) e).getErrorList()));
                }
            }
        }
    }
}
