package com.objectfrontier.training.java.service.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.objectfrontier.training.java.service.pojo.Address;
import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.service.AddressService;
import com.objectfrontier.training.java.service.service.ConnectionManager;
import com.objectfrontier.training.java.service.service.PersonService;
import com.objectfrontier.training.java.service.test.AppException;
import com.objectfrontier.training.java.service.test.JsonConverter;

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
                ConnectionManager.destroyConnection(con);
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

                ConnectionManager.destroyConnection(con);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        BufferedReader reader = req.getReader();
        List<String> jsonLines = reader.lines().collect(Collectors.toList());
        String addressJson = String.join("", jsonLines);
        String id = req.getParameter("id");
        PrintWriter writer = res.getWriter();
        PrintWriter out = res.getWriter();

        try {
            con = ConnectionManager.createConnection().getConnection();
            Address input = JsonConverter.toObject(addressJson, Address.class);
            Address address = new AddressService().create(con, input);
            out.println(JsonConverter.toJson(address));
            res.setStatus(HttpStatus.SC_OK);
            con.close();
        } catch (AppException e) {
            res.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            out.write(JsonConverter.toJson(e.getErrorList()));
        } catch (Exception e) {
            res.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            ConnectionManager.destroyConnection(con);
            e.printStackTrace();
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
                ConnectionManager.destroyConnection(con);
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
                ConnectionManager.destroyConnection(con);
            }
        }
    }
}
