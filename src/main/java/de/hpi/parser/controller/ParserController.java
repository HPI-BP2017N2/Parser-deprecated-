package de.hpi.parser.controller;

import de.hpi.parser.service.ParserService;
import de.hpi.restclient.dto.ParseOfferParameter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParserController {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService service;

    @Autowired
    public ParserController(ParserService service){
        setService(service);
    }

    @RequestMapping(value = "/parseOffer", method = RequestMethod.POST,  produces = "application/json")
    public void parseOffer(@RequestBody ParseOfferParameter parameter){
        System.out.println(getService().parseOffer(parameter.getHtml(), parameter.getShopID()));
    }
}
