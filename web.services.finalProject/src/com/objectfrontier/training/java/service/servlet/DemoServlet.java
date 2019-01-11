package com.objectfrontier.training.java.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.service.PersonService;
import com.objectfrontier.training.java.service.util.ConnectionManager;
import com.objectfrontier.training.java.service.util.JsonConverter;


public class DemoServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection con = null;
        String id = request.getParameter("id");
        out.println("<html><head><h1> WiNtEr Is CoMiNg!!</h1></head></html>");

        if(Objects.isNull(id)) {
            try {

                con = ConnectionManager.createConnection().getConnection();
                PersonService readAllPerson = new PersonService();
                List<Person> personList = readAllPerson.readAll(con);
                for(Person list : personList) {
                    out.write(list.toString() + "</br>");
                }
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        } else {
            try {

            con = ConnectionManager.createConnection().getConnection();
            Person readPerson = new PersonService().read(con, Long.parseLong(id), "y");
            out.write(JsonConverter.toJson(readPerson));
            con.close();
            } catch (Exception e) {

            e.printStackTrace();
            }
        }
    }
}
