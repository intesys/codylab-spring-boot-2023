package it.intesys.academy.service;

import it.intesys.academy.dto.MessageDTO;

import java.util.Date;

public class PropertyMessageService {

    private final String messageString;


    public PropertyMessageService(String message) {
        this.messageString = message;
    }

    public MessageDTO getMessage() {

        if (messageString.startsWith("Hello")) {
            throw new RuntimeException("BORING!");
        }

        var message = new MessageDTO();
        message.setText(messageString);
        message.setTimestamp(new Date());

        return message;
    }

}
