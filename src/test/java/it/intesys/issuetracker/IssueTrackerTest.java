package it.intesys.issuetracker;

import it.intesys.academy.IssueTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTrackerTest {

    Logger log = LoggerFactory.getLogger(IssueTrackerTest.class);
    @Test
    public void testGetMessage() {
        log.info("Test message");

        String message = IssueTracker.getMessage();
        Assertions.assertEquals("Hello from test properties", message);
    }
}
