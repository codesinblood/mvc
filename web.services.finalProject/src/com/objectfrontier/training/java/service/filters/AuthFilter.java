package com.objectfrontier.training.java.service.filters;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter{

    FilterConfig config;

    @Override
    public void init(FilterConfig config) throws ServletException {

        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Authentication filter -->");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        if (!(Objects.isNull(session))) {
            chain.doFilter(request, response);
        } else if (request.getRequestURI().equalsIgnoreCase("/ws.ccc/do/login")) {
            System.out.println(request.getRequestURI());
            chain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        System.out.println("<-- Authentication filter");
    }


    @Override
    public void destroy() {

    }
}
