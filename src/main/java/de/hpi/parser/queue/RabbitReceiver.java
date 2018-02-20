package de.hpi.parser.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hpi.parser.controller.ParserController;
import de.hpi.restclient.dto.CrawledPage;
import de.hpi.restclient.dto.FinishedShop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
public class RabbitReceiver {

    @Getter(AccessLevel.PRIVATE) private Logger logger = Logger.getRootLogger();
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserController controller;

    @Autowired
    public RabbitReceiver(ParserController controller) {
        setController(controller);
    }

    public void receiveMessage(byte[] message) throws JsonProcessingException {
        CrawledPage page = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            page = mapper.readValue(new String(message), CrawledPage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        getLogger().info("Received <" + page.getUrl() + "> from queue");
        getController().parse(page);
    }
}