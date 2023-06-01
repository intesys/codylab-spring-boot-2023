package it.intesys.IssueTracker.service;

import it.intesys.academy.repository.AppPropertiesConfigRepository;
import it.intesys.academy.repository.DummyConfigRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import it.intesys.academy.service.MessageService;

public class MessageServiceTest {


    @Test
    public void getMessageTest(){
        String message = new MessageService(new DummyConfigRepository()).getMessageTest();
        Assertions.assertEquals("app.message value", message);
    }
}
