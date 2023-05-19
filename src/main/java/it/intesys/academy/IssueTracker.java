package it.intesys.academy;

public class IssueTracker {

    public static void main(String[] args) {

        String message = AppConfiguration.appProperties().getProperty("app.message");
        System.out.println(message);
    }
}
