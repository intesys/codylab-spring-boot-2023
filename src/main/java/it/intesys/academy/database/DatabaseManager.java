package it.intesys.academy.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    public static void closeResultSet(ResultSet resultSet) {

        try {

            if (resultSet != null) resultSet.close();

        }
        catch (SQLException e) {
                log.error("Some errors occur during closing connection", e);
        }

    }

}
