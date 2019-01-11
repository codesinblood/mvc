package com.objectfrontier.training.java.service.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.objectfrontier.training.java.service.pojo.Person;

public class AuthorizationFilter implements Filter {


    @Override
    public void init(FilterConfig congif) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Authorization filter --> ");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        boolean admin = (boolean) session.getAttribute("admin");
        Person person = (Person) session.getAttribute("person");
        if(admin == true) {
            chain.doFilter(req, res);
        }
        else if((req.getParameter("field").equalsIgnoreCase("update"))
                || (req.getParameter("field").equalsIgnoreCase("read"))) {

            chain.doFilter(req, res);

        }

        System.out.println("<-- Authorization filter");
    }

    @Override
    public void destroy() {


    }
}
