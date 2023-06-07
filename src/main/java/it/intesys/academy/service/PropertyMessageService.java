package it.intesys.academy.service;

import it.intesys.academy.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PropertyMessageService {

    private final String messageString;


    public PropertyMessageService(@Value("${app.message}") String message) {
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
