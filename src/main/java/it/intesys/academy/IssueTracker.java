package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.dto.MessageDTO;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import java.util.Date;

public class IssueTracker {

    private static Logger log= LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {

        MessageDTO messageDTO = new MessageDTO(getMessage(), new Date());

        Javalin.create()
                .get("/codylab", ctx -> ctx.json(messageDTO))
                .start(Integer.valueOf(AppConfig.appProperties().getProperty(("server.port"))));

       // System.out.println(message);
        log.info(getMessage());
    }
    public static String getMessage(){
        return AppConfig.appProperties().getProperty("app.message");
    }
}
