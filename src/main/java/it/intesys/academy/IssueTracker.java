package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.service.PropertyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTracker {

    private static Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {
        var applicationPort = Integer.parseInt(AppConfiguration.appProperties().getProperty("app.port"));
        log.info("Start application on port {}", applicationPort);

        var messageService = new PropertyMessageService();

        var app = Javalin.create()
                .get("/", ctx -> ctx.json(messageService.getMessage()))
                .start(applicationPort);
    }


}
