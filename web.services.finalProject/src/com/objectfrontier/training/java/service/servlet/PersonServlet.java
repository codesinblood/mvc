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

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.service.PersonService;
import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.ConnectionManager;
import com.objectfrontier.training.java.service.util.JsonConverter;

public class PersonServlet extends HttpServlet {

    Connection con;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/Json");
        PrintWriter out = res.getWriter();
        String id = req.getParameter("id");

        out.println("<html><head><h1> WiNtEr Is CoMiNg!!</h1></head></html>");

        if(Objects.isNull(id)) {
            try {

                con = ConnectionManager.createConnection().getConnection();
                PersonService readAllPerson = new PersonService();
                List<Person> personList = readAllPerson.readAll(con);
                for(Person list : personList) {
                    out.write(JsonConverter.toJson(list));
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        } else {
            try {

                con = ConnectionManager.createConnection().getConnection();
                Person readPerson = new PersonService().read(con, Long.parseLong(id), "n");
                out.write(JsonConverter.toJson(readPerson));
                con.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html");
        BufferedReader reader = req.getReader();
        List<String> jsonLines = reader.lines().collect(Collectors.toList());
        String personJson = String.join("", jsonLines);
        String id = req.getParameter("id");
        PrintWriter out = res.getWriter();

        try {
            con = ConnectionManager.createConnection().getConnection();
            Person input = JsonConverter.toObject(personJson, Person.class);
            Person person = new PersonService().create(con, input);
            out.println(JsonConverter.toJson(person));
            res.setStatus(HttpStatus.SC_OK);
            con.close();
        } catch (AppException e) {
            res.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            out.write(JsonConverter.toJson(e.getErrorList()));
        } catch (Exception e) {
            res.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("application/Json");
        BufferedReader reader = req.getReader();
        List<String> jsonLines = reader.lines().collect(Collectors.toList());
        String personJson = String.join("", jsonLines);
        String id = req.getParameter("id");
        PrintWriter writer = res.getWriter();

        if(Objects.isNull(id)) {
            try {
                con = ConnectionManager.createConnection().getConnection();
                Person input = JsonConverter.toObject(personJson, Person.class);
                Person person = new PersonService().update(con, input);
                writer.write(JsonConverter.toJson(person));
                con.close();

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        else {
            try {
                con = ConnectionManager.createConnection().getConnection();
                Person input = JsonConverter.toObject(personJson, Person.class);
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
