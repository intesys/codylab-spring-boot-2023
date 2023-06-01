package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.MessageDTO;
import java.sql.Connection;

import java.util.Date;

public class ProjectService {
    public MessageDTO testConnection(){
        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());

        Connection conn = DatabaseManager.getConnection();

        if(conn != null){
            message.setText("Connession correctly taken!");
            DatabaseManager.closeConnection(conn);
        }
        else{
            message.setText("Oooops we have some problem!");
        }

        return message;
    }
    public static boolean existTable(String tableName){
        Connection conn = DatabaseManager.getConnection();
        try{

            return conn.createStatement().execute("SELECT * FROM " + tableName);
        }
        catch(Exception e){
            DatabaseManager.closeConnection(conn);
        }
        return Boolean.FALSE;
    };
};

