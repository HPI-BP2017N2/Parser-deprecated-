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

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private Parser parser;

    public ParserService() {
        setParser(new Parser());
    }

    public String parseHtmlWithSpecifiedRule(String html, String ruleAsJsonString) {
        return getParser().parseHtmlWithRuleAsJson(html, ruleAsJsonString);
    }

    public String extractJsonLdFromHtml(String html) throws FileNotFoundException {
        return getParser().parseHtmlWithExistingRule(html, getRULES_DIR() + File.separator + getJSON_LD_RULE());
    }
}
