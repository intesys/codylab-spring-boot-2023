package it.intesys.issuetracker.service;

import it.intesys.academy.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageServiceTest {

    @Test
    public void testGetMessage() {
        String message = new MessageService(new DummyConfigRepository())
                .getMessage("app.message");
        Assertions.assertEquals("app.message value", message);
    }

    @Test
    public void testGetMessageError() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new MessageService(new DummyConfigRepository()).getMessage("Hello"));
    }

}
