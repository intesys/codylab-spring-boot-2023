package it.intesys.IssueTracker;

import it.intesys.academy.IssueTracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueTrackerTest {
    @Test
    public void getMessageTest(){
        String message = IssueTracker.getMessage();
        Assertions.assertEquals("hello from properties", message);
    }
}
