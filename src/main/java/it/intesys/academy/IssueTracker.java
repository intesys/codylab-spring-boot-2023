package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssueTracker {

    private static final Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {
        var applicationPort = Integer.parseInt(AppConfiguration.appProperties().getProperty("app.port"));
        log.info("Start application on port {}", applicationPort);

        var messageService = new PropertyMessageService();
        var projectService = new ProjectService();

        Javalin.create()
                .get("/", ctx -> ctx.json(messageService.getMessage()))
                .get("/connection", ctx -> ctx.json(projectService.testConnection()))
                .get("/list-projects", ctx -> ctx.json(projectService.readProjects()))
                .start(applicationPort);
    }

}
