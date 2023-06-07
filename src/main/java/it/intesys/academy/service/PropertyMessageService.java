package it.intesys.academy.service;

import it.intesys.academy.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.Properties;

public class PropertyMessageService {

    private final Properties properties;

    public PropertyMessageService(@Qualifier("appProperties") Properties properties) {
        this.properties = properties;
    }

    public MessageDTO getMessage() {

        var messageString = properties.getProperty("app.message");

        if (messageString.startsWith("Hello")) {
            throw new RuntimeException("BORING!");
        }

        var message = new MessageDTO();
        message.setText(messageString);
        message.setTimestamp(new Date());

        return message;
    }

}
