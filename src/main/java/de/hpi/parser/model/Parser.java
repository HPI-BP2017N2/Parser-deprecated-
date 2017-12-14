package de.hpi.parser.model;

import de.hpi.parser.model.data.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.json.*;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    //convenience
    public static String parse(String html, JsonObject rulesJsonObject){
        Document htmlDocument = Jsoup.parse(html);
        return extractData(rulesJsonObject, htmlDocument).toString();
    }

    //actions
    private static JsonObject extractData(JsonObject rulesJsonObject, Document htmlDocument) {
        JsonObjectBuilder dataObjectBuilder = Json.createObjectBuilder();
        for (String key : rulesJsonObject.keySet()){
            JsonObject resultJsonObject = extractProductAttribute(htmlDocument, rulesJsonObject, key);
            dataObjectBuilder.add(key, resultJsonObject);
        }
        return dataObjectBuilder.build();
    }

    private static JsonObject extractProductAttribute(Document htmlDocument, JsonObject rulesJsonObject, String key) {
        JsonObjectBuilder resultObjectBuilder = Json.createObjectBuilder();
        List<JsonObject> jsonRules = rulesJsonObject.getJsonArray(key).getValuesAs(JsonObject.class);
        for (int iJsonRule = 0; iJsonRule < jsonRules.size(); iJsonRule++) {
            Rule rule = Rule.parseRule(jsonRules.get(iJsonRule));
            JsonArray resultJsonArray = extractMatches(htmlDocument, rule);
            resultObjectBuilder.add(Integer.toString(iJsonRule), resultJsonArray);
        }
        return resultObjectBuilder.build();
    }

    private static JsonArray extractMatches(Document htmlDocument, Rule rule) {
        JsonArrayBuilder ruleArrayBuilder = Json.createArrayBuilder();
        List<String> results = extractMatchesAsList(htmlDocument, rule);
        for (String result : results){
            ruleArrayBuilder.add(result);
        }
        return ruleArrayBuilder.build();
    }

    private static List<String> extractMatchesAsList(Document htmlDocument, Rule rule) {
        List<String> results = new LinkedList<>();
        String result;
        for (Element element : htmlDocument.select(rule.getXPath())){
            result = extract(rule, element);
            if (isResultValid(result)){
                results.add(result);
            }
        }
        return results;
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
