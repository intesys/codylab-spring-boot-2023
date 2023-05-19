package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class IssueTracker {

    private static Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {
        var applicationPort = Integer.parseInt(AppConfiguration.appProperties().getProperty("app.port"));
        log.info("Start application on port {}", applicationPort);

        var message = new MessageDTO();
        message.setText(getMessage());
        message.setTimestamp(new Date());

        var app = Javalin.create()
                .get("/", ctx -> ctx.json(message))
                .start(applicationPort);
    }

    public static String getMessage() {
        return AppConfiguration.appProperties().getProperty("app.message");
    }
}
