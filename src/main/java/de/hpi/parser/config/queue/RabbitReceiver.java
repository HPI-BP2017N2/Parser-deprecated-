package de.hpi.parser.config.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hpi.parser.controller.ParserController;
import de.hpi.restclient.dto.CrawledPage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {

    @Getter(AccessLevel.PRIVATE) private Logger logger = Logger.getRootLogger();
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserController controller;

    @Autowired
    public RabbitReceiver(ParserController controller) {
        setController(controller);
    }

    public void receiveMessage(CrawledPage page) throws JsonProcessingException {
        getLogger().info("Received <" + page.getUrl() + "> from queue");

        getController().parsePage(page);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}