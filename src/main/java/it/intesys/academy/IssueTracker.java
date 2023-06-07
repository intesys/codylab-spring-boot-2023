package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

public class IssueTracker {

    private static final Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {
        ApplicationContext springContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        Properties appProperties = springContext.getBean("appProperties", Properties.class);
        var applicationPort = Integer.parseInt(appProperties.getProperty("app.port"));
        log.info("Start application on port {}", applicationPort);

        PropertyMessageService messageService = springContext.getBean(PropertyMessageService.class);
        ProjectService projectService = springContext.getBean(ProjectService.class);

        Javalin.create()
                .get("/", ctx -> ctx.json(messageService.getMessage()))
                .get("/list-projects", ctx -> {
                    String username = ctx.queryParam("username");
                    ctx.json(projectService.readProjects(username));
                })
                .start(applicationPort);
    }

}
