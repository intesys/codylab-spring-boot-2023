package it.intesys.academy.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    public static final String USER = "sa";
    public static final String PASSWORD = "";

    public static Connection getConnection() {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection("jdbc:h2:~/lesson-1", USER, PASSWORD);

        }
        catch (SQLException e) {
            log.error("Some errors occur during database connection", e);
        }

        return conn;

    }

    public static void closeConnection(Connection conn) {

        try {

            if (conn != null) conn.close();

        }
        catch (SQLException e) {
                log.error("Some errors occur during closing connection", e);
        }

    }
}
