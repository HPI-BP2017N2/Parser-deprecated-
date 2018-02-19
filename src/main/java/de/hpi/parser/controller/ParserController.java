package de.hpi.parser.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hpi.parser.dto.ParseParameter;
import de.hpi.parser.model.JsonConverter;
import de.hpi.parser.model.data.Rule;
import de.hpi.parser.respository.OfferJsonRepository;
import de.hpi.parser.service.ParserService;
import de.hpi.restclient.dto.CrawledPage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.valves.CrawlerSessionManagerValve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
public class ParserController {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private OfferJsonRepository repository;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService service;

    @Autowired
    public ParserController(ParserService service, OfferJsonRepository repository){
        setService(service);
        setRepository(repository);
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST, produces = "application/json")
    public String parse(@RequestBody ParseParameter parameter) throws JsonProcessingException {
        String schemaData = getService().parseHtmlWithSchemaOrg(parameter.getHtml());
        String jsonLDData = getService().parseHtmlWithJsonLD(parameter.getHtml());

        //getRepository().save(schemaData + System.lineSeparator() + jsonLDData);
        return "[" + schemaData + ",\n" + jsonLDData + "\n]";
    }

    public void parsePage(CrawledPage page) throws JsonProcessingException {
        String schemaData = getService().parseHtmlWithSchemaOrg(page.getHtmlSource());
        String jsonLDData = getService().parseHtmlWithJsonLD(page.getHtmlSource());

        getRepository().save(schemaData + System.lineSeparator() + jsonLDData);
    }

}
