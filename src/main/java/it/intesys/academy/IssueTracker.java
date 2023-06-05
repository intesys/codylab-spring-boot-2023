package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.service.PropertyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTracker {

    private static final Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {
        var applicationPort = Integer.parseInt(AppConfiguration.appProperties().getProperty("app.port"));
        log.info("Start application on port {}", applicationPort);

        var messageService = new PropertyMessageService();
        var projectService = AppConfiguration.projectService();

        Javalin.create()
                .get("/", ctx -> ctx.json(messageService.getMessage()))
                .get("/list-projects", ctx -> {
                    String username = ctx.queryParam("username");
                    ctx.json(projectService.readProjects(username));
                })
                .start(applicationPort);
    }

}
