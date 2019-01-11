package com.objectfrontier.training.java.service.filters;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.objectfrontier.training.java.service.util.ConnectionManager;

public class TransactionFilter implements Filter {

    ConnectionManager connManager;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Transaction Filter -->");
        connManager.getConn();
        Connection connection = ConnectionManager.myThreadLocal.get();
        try {

            System.out.println(connection);
            chain.doFilter(req, res);

            connManager.destroyConnection(connection);
        } catch (Exception e) {
            connManager.destroyConnection(connection);
            throw e;
        }

        System.out.println("<-- Transaction filter");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

        connManager = new ConnectionManager();
    }

}
