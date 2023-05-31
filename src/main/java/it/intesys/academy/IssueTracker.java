package it.intesys.academy;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTracker {
    private static Logger log = LoggerFactory.getLogger(IssueTracker.class);
    public static void main(String[] args) {

        String applicationPort = AppConfig.appProperties().getProperty("app.port");
        Javalin.create()
                .get("/codylab", ctx -> ctx.result(getMessage()))
                .start(Integer.parseInt(applicationPort));
    }

    public static String getMessage(){
       return AppConfig.appProperties().getProperty("app.message");
    }
}
