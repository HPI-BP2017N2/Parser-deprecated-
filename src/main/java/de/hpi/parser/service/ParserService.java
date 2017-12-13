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

    //constants
    @Getter(AccessLevel.PRIVATE) private static final String RULES_DIR = "rules", JSON_LD_RULE = "jsonLD.json";

    public String parseHtmlWithSpecifiedRule(String html, String ruleAsJsonString) {
        return Parser.parseHtmlWithRuleAsJson(html, ruleAsJsonString);
    }

    public String extractJsonLdFromHtml(String html) throws FileNotFoundException {
        return Parser.parseHtmlWithExistingRule(html, getRULES_DIR() + File.separator + getJSON_LD_RULE());
    }
}
