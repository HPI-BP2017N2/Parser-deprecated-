package de.hpi.parser.controller;

import de.hpi.parser.service.ParserService;
import de.hpi.restclient.dto.GetRulesResponse;
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

    @RequestMapping(value = "/parseOffer", method = RequestMethod.POST)
    public void parseOffer(@RequestBody ParseOfferParameter parameter){
        getService().parseOffer(parameter.getHtml(), parameter.getShopID());
    }

    @RequestMapping(value = "/rulesCallback", method = RequestMethod.POST)
    public void rulesCallback(@RequestBody GetRulesResponse parameter){
        getService().handleRulesResponse(parameter.getRules(), parameter.getShopID());
    }
}
