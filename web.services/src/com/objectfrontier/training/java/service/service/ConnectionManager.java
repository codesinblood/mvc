package com.objectfrontier.training.java.service.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {

    public static Connection dbAccess() throws Exception {

        Properties prop = new Properties();
        InputStream input= new FileInputStream("resource//connection.properties");
        prop.load(input);
        Connection connectToDB = DriverManager.getConnection(prop.getProperty("server"), prop.getProperty("user"), prop.getProperty("password"));
        connectToDB.setAutoCommit(false);
        return connectToDB;
    }

    public static void destroyConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
           throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L1);
        }
    }

    public static HikariDataSource createConnection() throws SQLException {

        HikariConfig config = new HikariConfig("resources//hikariCP.properties");
        HikariDataSource ds = new HikariDataSource(config);
        ds.setMaximumPoolSize(1);
        return ds;
        }

    public static Connection getConnection() throws SQLException {

        HikariConfig config = new HikariConfig("resources//hikariCP.properties");
        HikariDataSource ds = new HikariDataSource(config);
        ds.setMaximumPoolSize(2);
        Connection connect = ds.getConnection();
        return connect;
        }
}
