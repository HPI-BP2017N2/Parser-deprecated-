package de.hpi.parser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hpi.parser.dto.ParseParameter;
import de.hpi.parser.respository.ParsedOfferRepository;
import de.hpi.parser.service.ParserService;
import de.hpi.restclient.dto.CrawledPage;
import de.hpi.restclient.dto.ParsedOffer;
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

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParsedOfferRepository repository;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService service;

    @Autowired
    public ParserController(ParserService service, ParsedOfferRepository repository){
        setService(service);
        setRepository(repository);
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST, produces = "application/json")
    public String parse(@RequestBody CrawledPage page) throws JsonProcessingException {
        String schemaData = getService().parseHtmlWithSchemaOrg(page.getHtmlSource());
        String jsonLDData = getService().parseHtmlWithJsonLD(page.getHtmlSource());

        getRepository().save(page.getShopID(), schemaData + System.lineSeparator() + jsonLDData);
        getRepository().save(page.getShopID(), new ParsedOffer());
        return "[" + schemaData + ",\n" + jsonLDData + "\n]";
    }

}
