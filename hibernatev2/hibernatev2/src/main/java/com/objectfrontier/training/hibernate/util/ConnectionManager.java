package com.objectfrontier.training.hibernate.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionManager {

    public static void destroyConnection(Connection conn, Boolean choice) throws SQLException {

        if (choice) {
            conn.commit();
            conn.close();
        } else {
            conn.rollback();
            conn.close();
        }
    }

    private static HikariDataSource ds;
    public static ThreadLocal<Connection> myThreadLocal = new ThreadLocal<>();
    public void getConn() {
        myThreadLocal.set(getConnection());
    }

    {
        HikariConfig config = new HikariConfig("/hikariCP.properties");
        config.setMaximumPoolSize(2);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection () {
        try {
            Connection con = ds.getConnection();
            con.setAutoCommit(false);
            return con;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L1);
        }
    }

    public static Connection getDBConnection() throws SQLException {

        try {
        HikariConfig config = new HikariConfig("/hikariCP.properties");
        HikariDataSource ds = new HikariDataSource(config);
        ds.setMaximumPoolSize(2);
        ds.setAutoCommit(false);
        Connection connect = ds.getConnection();
        return connect;
        } catch (Exception e){
            e.printStackTrace();
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L1);
        }
    }

    public static HikariDataSource createConnection() throws SQLException {

        try {
         HikariConfig config = new HikariConfig("/hikariCP.properties");
         HikariDataSource ds = new HikariDataSource(config);
         ds.setMaximumPoolSize(2);
//         ds.setAutoCommit(false);
         return ds;
         }  catch (Exception e){
             throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR_L1);
         }
     }
}
