package com.objectfrontier.training.java.service.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import com.objectfrontier.training.java.service.util.AppException;
import com.objectfrontier.training.java.service.util.JsonConverter;

public class ErrorFilter implements Filter {

    FilterConfig config;

    @Override
    public void init(FilterConfig config) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        PrintWriter out = res.getWriter();
        System.out.println("Error Filter -->");
        try {
            chain.doFilter(request, response);
            response.setStatus(HttpStatus.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            if (e instanceof AppException) {
                System.out.println(JsonConverter.toJson(((AppException) e).getErrorList()));
                out.write(JsonConverter.toJson(((AppException) e).getErrorList()));
            }
        }
        System.out.println("<-- Error Filter");
    }


    @Override
    public void destroy() {

    }

}
