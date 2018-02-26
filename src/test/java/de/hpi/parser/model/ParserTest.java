package de.hpi.parser.model;

import de.hpi.restclient.pojo.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
class ParserTest {

    private Rules rules;

    @BeforeEach
    void setUp() {
        setRules(createRulesForNotebooksBilliger());
    }

    @Test
    void parse() throws URISyntaxException, IOException {
        URI fileURI = getClass().getClassLoader().getResource("Microsoft Surface Dock" +
                ".html").toURI();
        Document htmlDocument = Jsoup.parse(new File(fileURI), "UTF-8");
        ExtractedDataMap map = Parser.parse(htmlDocument.outerHtml(), getRules());
        assertEquals("EUR", map.getData().get(OfferAttribute.CURRENCY).getValue());
        assertEquals("Zubehör & Kabel", map.getData().get(OfferAttribute.CATEGORY_PATHS).getValue());
    }

    private Rules createRulesForNotebooksBilliger() {
        Rules result = new Rules();
        EnumMap<OfferAttribute, Rule> rules = new EnumMap<>(OfferAttribute.class);
        rules.put(OfferAttribute.CATEGORY_PATHS, new Rule(Arrays.asList(new RuleEntry("#path_text div:nth-of-type(2) a:nth-of-type(1)" +
                        " span:nth-of-type(1)")), OfferAttribute.CATEGORY_PATHS));
        RuleEntry currencyRuleEntry = new RuleEntry("span[itemprop=priceCurrency]");
        currencyRuleEntry.setAttribute("content");
        rules.put(OfferAttribute.CURRENCY, new Rule(Arrays.asList(currencyRuleEntry), OfferAttribute.CURRENCY));
        result.setRules(rules);
        return result;
    }
}