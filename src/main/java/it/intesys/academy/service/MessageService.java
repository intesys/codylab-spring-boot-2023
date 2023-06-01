package it.intesys.academy.service;

import it.intesys.academy.repository.AppPropertiesConfigRepository;
import it.intesys.academy.repository.ConfigRepository;
import it.intesys.academy.repository.DummyConfigRepository;

public class MessageService {
    public String getMessage(){
        ConfigRepository configRepository =  new AppPropertiesConfigRepository();
        ConfigRepository dummyConfigRepository = new DummyConfigRepository();
        AppPropertiesConfigRepository property = new AppPropertiesConfigRepository();
        return dummyConfigRepository.getProperty("app.message");
    }
}
