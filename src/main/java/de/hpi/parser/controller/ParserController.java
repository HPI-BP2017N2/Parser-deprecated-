package de.hpi.parser.controller;

import de.hpi.parser.dto.ParseParameter;
import de.hpi.parser.respository.OfferJsonRepository;
import de.hpi.parser.service.ParserService;
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

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private OfferJsonRepository repository;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService service;

    @Autowired
    public ParserController(ParserService service, OfferJsonRepository repository){
        setService(service);
        setRepository(repository);
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST)
    public void parse(@RequestBody ParseParameter parameter){
        String schemaData = getService().parseHtmlWithSchemaOrg(parameter.getHtml());
        String jsonLDData = getService().parseHtmlWithJsonLD(parameter.getHtml());

        getRepository().save(schemaData + System.lineSeparator() + jsonLDData);
    }

}
