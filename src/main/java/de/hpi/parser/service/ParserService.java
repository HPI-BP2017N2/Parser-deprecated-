package de.hpi.parser.service;

import de.hpi.parser.model.Parser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

@Service
public class ParserService {

    public String parseHtmlWithSpecifiedRule(String html, String ruleAsJsonString) {
        return Parser.parseHtmlWithRuleAsJson(html, ruleAsJsonString);
    }

}
