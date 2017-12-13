package de.hpi.parser.controller;

import de.hpi.parser.dto.ExtractJsonLdParameter;
import de.hpi.parser.dto.ExtractJsonLdResponse;
import de.hpi.parser.dto.ParseWithRuleParameter;
import de.hpi.parser.dto.ParseWithRuleResponse;
import de.hpi.parser.model.data.OfferRepository;
import de.hpi.parser.service.ParserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
public class ParserController {

    @Autowired
    @Getter(AccessLevel.PRIVATE) private OfferRepository repository;

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService){
        setParserService(parserService);
    }

    @RequestMapping(value = "/parseWithRule", method = RequestMethod.POST,  produces = "application/json")
    public ParseWithRuleResponse parseHtmlWithSpecifiedRule(@RequestBody ParseWithRuleParameter parameter){
        return new ParseWithRuleResponse(getParserService().parseHtmlWithSpecifiedRule(parameter.getHtml(), parameter
                .getRuleAsJson()));
    }

    @RequestMapping(value = "/extractJsonLd", method = RequestMethod.POST,  produces = "application/json")
    public ExtractJsonLdResponse extractJsonLdFromHtml(@RequestBody ExtractJsonLdParameter parameter) throws FileNotFoundException {
        return new ExtractJsonLdResponse(getParserService().extractJsonLdFromHtml(parameter.getHtml()));
    }

    @RequestMapping(value = "/offerCount", method = RequestMethod.POST)
    public void offerCount() {
        System.out.println(getRepository().offerCount());
    }

}
