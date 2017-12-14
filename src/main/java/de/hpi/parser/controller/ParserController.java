package de.hpi.parser.controller;

import de.hpi.parser.dto.ParseWithRuleParameter;
import de.hpi.parser.dto.ParseWithRuleResponse;
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

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService){
        setParserService(parserService);
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST,  produces = "application/json")
    public ParseWithRuleResponse parseHtmlWithSpecifiedRule(@RequestBody ParseWithRuleParameter parameter){
        String extractedData = getParserService().parseHtmlWithSpecifiedRule(parameter.getHtml(), parameter
                .getRuleAsJson());
        return new ParseWithRuleResponse(extractedData);
    }

}
