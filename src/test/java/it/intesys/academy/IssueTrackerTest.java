package it.intesys.academy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IssueTrackerTest {

    @Test
    @DisplayName("The message is correctly read from application.properties")
    void getMessage() {
        Assertions.assertEquals("Hello from test properties!", IssueTracker.getMessage());
    }

}
