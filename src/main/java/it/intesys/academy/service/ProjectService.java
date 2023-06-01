package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.MessageDTO;

import java.sql.Connection;
import java.util.Collection;
import java.util.Date;

public class ProjectService {
    public static MessageDTO testConnection(){
        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());

        Connection conn = DatabaseManager.getConnection();

        if(conn != null){
            message.setText("Connection");
            DatabaseManager.closeConnection(conn);
        }else{
            message.setText("Connection failure");
        }

        return message;
    }

    public static boolean existTable(String tableName){
        Connection conn = DatabaseManager.getConnection();

        try{
            return conn.createStatement().execute("SELECT * FROM "+tableName);
        }catch (Exception e){
            DatabaseManager.closeConnection(conn);
        }
        return false;
    }
}
