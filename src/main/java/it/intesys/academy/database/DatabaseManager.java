package it.intesys.academy.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
    public static final String USER = "sa";
    public static final String PASSWORD = "password";

    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("C:\\Users\\matti\\OneDrive\\Desktop\\Stage\\06.01.2023\\lesson2.mv.db", USER, PASSWORD);

        }
        catch (SQLException ex){
            log.error("Errore type: ",ex);
        }
        finally {
            try {
                conn.close();
            }catch (SQLException e) {
                log.error("Error type:",e);
            }
        }
        return conn;
    }
}
