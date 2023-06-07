package it.intesys.academy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class IssueTrackerTest {

    @Test
    @DisplayName("The message is correctly read from application.properties")
    void getMessage() {
        Properties properties = new Properties();
        properties.put("app.message", "Hi from test properties!");
        Assertions.assertEquals("Hi from test properties!", new PropertyMessageService(properties).getMessage().getText());
    }

    @Test
    @DisplayName("Throws exception for boring message")
    void getBoringMessageException() {
        Properties properties = new Properties();
        properties.put("app.message", "Hello from test properties!");
        Assertions.assertThrows(RuntimeException.class, () -> new PropertyMessageService(properties).getMessage().getText());
    }

}
