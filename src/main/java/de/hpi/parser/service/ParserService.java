package de.hpi.parser.service;

import de.hpi.parser.model.Parser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParserService {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private Parser parser;

    public ParserService() {
        setParser(new Parser());
    }

    public String parseHtmlWithSepcifiedRule(String html, String ruleAsJsonString) throws IOException {
       return getParser().parseHtmlWithRuleAsJson(html, ruleAsJsonString);
    }

    public String extractJsonLdFromHtml(String html) throws IOException {
        return getParser().extractJsonLdFromHtml(html);
    }
}
