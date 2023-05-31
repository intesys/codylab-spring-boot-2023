package it.intesys.academy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTracker {

    private static Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {


        log.info("Message: {}", getMessage());
    }

    public static String getMessage() {
        return AppConfig.appProperties().getProperty("app.message");
    }
}
