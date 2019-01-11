package com.objectfrontier.training.java.service.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.objectfrontier.training.java.service.pojo.Person;
import com.objectfrontier.training.java.service.util.ConnectionManager;
import com.objectfrontier.training.java.service.util.JsonConverter;

public class AuthenticationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    Connection con;
    AuthenticationService authService = new AuthenticationService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = res.getWriter();
        BufferedReader reader = req.getReader();
        List<String> jsonLines = reader.lines().collect(Collectors.toList());
        String personJson = String.join("", jsonLines);
        System.out.format("Input JSON >> %s", personJson);
        //Converting JSON to Object
        Person input = JsonConverter.toObject(personJson, Person.class);
        Connection con = ConnectionManager.myThreadLocal.get();
        Person isadmin;
        try {
            isadmin = authService.validation(con , input);
            System.out.println(isadmin.toString());
            HttpSession session = req.getSession(true);
            session.setAttribute("admin", isadmin.getIsAdmin());
            session.setAttribute("person", isadmin);
            Cookie cookie = new Cookie("JSESSION_ID", session.getId());
            res.addCookie(cookie);
            out.write(JsonConverter.toJson(isadmin));
        } catch (SQLException e) {
            log(e.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
