package it.intesys.academy;

public class IssueTracker {

    public static void main(String[] args) {
        var message = AppConfig.appProperties().getProperty("app.message");
        System.out.println(message);
    }
}
