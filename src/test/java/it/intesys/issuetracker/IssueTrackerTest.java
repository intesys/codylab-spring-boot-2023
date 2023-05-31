package it.intesys.issuetracker;

import it.intesys.academy.IssueTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueTrackerTest {

    @Test
    public void testGetMessage() {
        String message = IssueTracker.getMessage();

        Assertions.assertEquals("Hello from test properties", message);

    }
}
