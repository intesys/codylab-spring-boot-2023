package it.intesys.academy.service;

import it.intesys.academy.repository.ConfigRepository;

public class MessageService {

    private ConfigRepository configRepository;

    public MessageService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public String getMessage(String messageKey) {

        String property = configRepository.getProperty(messageKey);

        if (property.startsWith("Hello")) {
            throw new RuntimeException("BORING!");
        }

        return property;
    }
}
