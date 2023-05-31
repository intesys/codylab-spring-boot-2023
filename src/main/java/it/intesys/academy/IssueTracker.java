package it.intesys.academy;

import io.javalin.Javalin;
import it.intesys.academy.dto.MessageDTO;
import it.intesys.academy.repository.AppPropertiesConfigRepository;
import it.intesys.academy.repository.ConfigRepository;
import it.intesys.academy.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class IssueTracker {

    private static Logger log = LoggerFactory.getLogger(IssueTracker.class);

    public static void main(String[] args) {

        ConfigRepository configRepository = new AppPropertiesConfigRepository();
        MessageService messageService = new MessageService(configRepository);

        MessageDTO messageDTO = new MessageDTO(messageService.getMessage("app.message"), new Date());

        String appPort = configRepository.getProperty("server.port");

        Javalin.create()
                .get("/codylab", ctx -> ctx.json(messageDTO))
                .start(Integer.parseInt(appPort));
    }


}
