package it.intesys.academy;

import it.intesys.academy.dto.MessageDto;
import it.intesys.academy.repository.AppPropertiesConfigRepository;
import it.intesys.academy.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.javalin.Javalin;

import java.util.Date;

public class IssueTracker {

    private static Logger logger = LoggerFactory.getLogger(IssueTracker.class);
    public static void main(String[] args) {
        MessageService message = new MessageService();
        MessageDto messageDto = new MessageDto(message.getMessage(), new Date());
        AppPropertiesConfigRepository property = new AppPropertiesConfigRepository();
        //String message = AppConfig.appProperties().getProperty("app.message");
        logger.info("Messagge: {}", message.getMessage());

        Javalin.create()
                .get("/codyLab", ctx -> ctx.json(messageDto))
                .start(Integer.valueOf(property.getProperty("server.port")));

    }

}
