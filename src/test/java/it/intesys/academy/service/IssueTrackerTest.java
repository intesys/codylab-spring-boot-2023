package it.intesys.academy.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssueTrackerTest {

    @Test
    @DisplayName("The message is correctly read from application.properties")
    void getMessage() {
        Assertions.assertEquals("Hi from test properties!", new PropertyMessageService("Hi from test properties!").getMessage().getText());
    }

    @Test
    @DisplayName("Throws exception for boring message")
    void getBoringMessageException() {
        Assertions.assertThrows(RuntimeException.class, () -> new PropertyMessageService("Hello from test properties!").getMessage().getText());
    }

}
