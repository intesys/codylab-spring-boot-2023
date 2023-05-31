package it.intesys.academy;

import it.intesys.academy.dto.MessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.javalin.Javalin;

import java.util.Date;

public class IssueTracker {

    private static Logger logger = LoggerFactory.getLogger(IssueTracker.class);
    public static void main(String[] args) {
        MessageDto messageDto = new MessageDto(getMessage(), new Date());
        //String message = AppConfig.appProperties().getProperty("app.message");
        logger.info("Messagge: {}", getMessage());

        Javalin.create()
                .get("/codyLab", ctx -> ctx.json(messageDto))
                .start(Integer.valueOf(AppConfig.appProperties().getProperty("server.port")));


    }
    public static String getMessage(){
        return AppConfig.appProperties().getProperty("app.message");
    }
}
