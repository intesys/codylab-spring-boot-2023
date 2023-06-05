package it.intesys.academy.database;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.*;


public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);


    public static final String USER = "sa";

    public static final String PASSWORD = "password";

    public static Connection  getConnection(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:h2:file:C:\\Utenti\\Alessia\\lesson-2\\lesson-1", USER, PASSWORD);
        }
        catch(SQLException e){
            log.error("Some errors occur during database connection", e);
        }

        return conn;
    }

    public static void closeConnection(Connection conn){
        try{
            if (conn != null){
                conn.close();
            }
        }
        catch(SQLException e){
            log.error("Some errors occur during closing connection", e);
        }
    }

    public static void closeStatement(Statement conn){
        try{
            if (conn != null){
                conn.close();
            }
        }
        catch(SQLException e){
            log.error("Some errors occur during closing connection", e);
        }
    }

    public static void closeResult(ResultSet conn){
        try{
            if (conn != null){
                conn.close();
            }
        }
        catch(SQLException e){
            log.error("Some errors occur during closing connection", e);
        }
    }

}
