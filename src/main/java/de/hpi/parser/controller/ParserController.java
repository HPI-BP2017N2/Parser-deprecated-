package de.hpi.parser.controller;

import de.hpi.parser.service.ParserService;
import de.hpi.restclient.dto.CrawledPage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParserController {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService service;

    @Autowired
    public ParserController(ParserService service){
        setService(service);
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST, produces = "application/json")
    public void parse(@RequestBody CrawledPage page) {
        getService().parseOffer(page.getHtmlSource(), page.getShopID());
    }

}
