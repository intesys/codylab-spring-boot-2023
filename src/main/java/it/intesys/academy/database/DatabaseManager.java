package it.intesys.academy.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    public static final String USER = "sa";
    public static final String PASSWORD = "password";

    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:h2:C:\\Users\\matti\\OneDrive\\Desktop\\Stage\\06.01.2023\\lesson2", USER, PASSWORD);

        }
        catch (SQLException ex){
            log.error("Errore type: ",ex);
        }
        return conn;
    }
    public static void closeConnection(Connection conn){
        try{
            if(conn != null){
                conn.close();
            }
        }catch (SQLException ex){
            log.error("Error type: ",ex);
        }
    }
    public static void closeStatement(Statement stmt){
        try{
            if(stmt != null){
                stmt.close();
            }
        }catch (SQLException ex){
            log.error("Error type: ",ex);
        }
    }
    public static void closeResultSet(ResultSet set){
        try{
            if(set != null){
                set.close();
            }
        }catch (SQLException ex){
            log.error("Error type: ",ex);
        }
    }
}

