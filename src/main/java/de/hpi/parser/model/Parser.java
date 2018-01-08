package de.hpi.parser.model;

import de.hpi.parser.model.data.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.json.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Parser {

    //convenience
    public static HashMap<String, HashMap<Integer, List<String>>> parse(String html, HashMap<String, List<Rule>>
            rules){
        Document htmlDocument = Jsoup.parse(html);
        return extractData(rules, htmlDocument);
    }

    //actions
    private static HashMap<String, HashMap<Integer, List<String>>> extractData(HashMap<String, List<Rule>> rules,
                                                                          Document htmlDocument) {
        HashMap<String, HashMap<Integer, List<String>>> extractedData = new HashMap<>();
        for (String attribute : rules.keySet()){
            HashMap<Integer, List<String>> result = extractProductAttribute(htmlDocument, rules.get(attribute));
            extractedData.put(attribute, result);
        }
        return extractedData;
    }

    private static HashMap<Integer, List<String>> extractProductAttribute(Document htmlDocument, List<Rule> rules) {
        HashMap<Integer, List<String>> extractedProductAttribute = new HashMap<>();
        for (int iSelector = 0; iSelector < rules.size(); iSelector++) {
            List<String> matches = extractMatches(htmlDocument, rules.get(iSelector));
            if (!matches.isEmpty()) {
                extractedProductAttribute.put(iSelector, matches);
            }
        }
        return extractedProductAttribute;
    }

    private static List<String> extractMatches(Document htmlDocument, Rule rule) {
        List<String> matches = new LinkedList<>();
        String match;
        for (Element element : htmlDocument.select(rule.getXPath())){
            match = extract(rule, element);
            if (isResultValid(match)){
                matches.add(match);
            }
        }
        return matches;
    }

    private static String extract(Rule rule, Element element) {
        if (isAttributeSet(rule)){
            return element.attr(rule.getAttribute());
        }
        if (rule.isResultAsPlainText()) {
            return element.text();
        }
        return element.html();
    }

    //conditionals
    private static boolean isAttributeSet(Rule rule){
        return rule.getAttribute() != null;
    }

    private static boolean isResultValid(String result){
        return result != null && result.length() > 0;
    }

}
