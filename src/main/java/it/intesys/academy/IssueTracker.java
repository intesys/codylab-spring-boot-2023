package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class IssueTracker {

    private static final Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext springContext = SpringApplication.run(IssueTracker.class, args);

        Environment environment = springContext.getEnvironment();
        var applicationPort = environment.getProperty("app.port", Integer.class);
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
