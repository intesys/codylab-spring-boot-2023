package it.intesys.academy.service;

import it.intesys.academy.AppConfiguration;
import it.intesys.academy.dto.MessageDTO;

import java.util.Date;

public class PropertyMessageService {

    public MessageDTO getMessage() {

        var messageString = AppConfiguration.appProperties().getProperty("app.message");

        var message = new MessageDTO();
        message.setComment(messageString);

        return message;
    }

}
