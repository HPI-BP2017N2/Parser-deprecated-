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

import java.io.IOException;

@RestController
public class ParserController {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private ParserService parserService;

    @Autowired
    public ParserController(ParserService parserService){
        setParserService(parserService);
    }

    @RequestMapping(value = "/parseWithRule", method = RequestMethod.POST,  produces = "application/json")
    public ParseWithRuleResponse parseHtmlWithSpecifiedRule(@RequestBody ParseWithRuleParameter parameter){
        try {
            return new ParseWithRuleResponse(getParserService().parseHtmlWithSepcifiedRule(parameter.getHtml(), parameter.getRuleAsJson()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
