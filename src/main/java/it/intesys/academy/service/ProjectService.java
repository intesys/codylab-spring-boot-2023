package it.intesys.academy.service;

import it.intesys.academy.database.DatabaseManager;
import it.intesys.academy.dto.MessageDTO;

import java.sql.Connection;
import java.util.Date;

public class ProjectService {

    public MessageDTO testConnection() {

        MessageDTO message = new MessageDTO();
        message.setTimestamp(new Date());

        Connection conn = DatabaseManager.getConnection();

        if (conn != null) {

            message.setText("Connection taken correctly!");
            DatabaseManager.closeConnection(conn);

        }
        else {

            message.setText("Attention! We have some problems!");

        }

        return message;

    }

}
