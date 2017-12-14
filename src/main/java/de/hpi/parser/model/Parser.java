package de.hpi.parser.model;

import de.hpi.parser.model.data.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    //convenience
    public static String parseHtmlWithRuleAsJson(String html, String ruleAsJson) {
        return parseHtmlFromRuleJsonObject(html, readRulesJson(ruleAsJson));
    }

    //actions
    private static String parseHtmlFromRuleJsonObject(String html, JsonObject rulesJsonObject){
        Document htmlDocument = Jsoup.parse(html);
        return extractData(rulesJsonObject, htmlDocument).toString();
    }

    private static JsonObject extractData(JsonObject rulesJsonObject, Document htmlDocument) {
        JsonObjectBuilder dataObjectBuilder = Json.createObjectBuilder();
        for (String key : rulesJsonObject.keySet()){
            JsonObject resultJsonObject = extractData(htmlDocument, rulesJsonObject, key);
            dataObjectBuilder.add(key, resultJsonObject);
        }
        return dataObjectBuilder.build();
    }

    private static JsonObject extractData(Document htmlDocument, JsonObject rulesJsonObject, String key) {
        JsonObjectBuilder resultObjectBuilder = Json.createObjectBuilder();
        List<JsonObject> jsonRules = rulesJsonObject.getJsonArray(key).getValuesAs(JsonObject.class);
        for (int iJsonRule = 0; iJsonRule < jsonRules.size(); iJsonRule++) {
            Rule rule = Rule.parseRule(jsonRules.get(iJsonRule));
            JsonArray resultJsonArray = getResultsForRule(htmlDocument, rule);
            resultObjectBuilder.add(Integer.toString(iJsonRule), resultJsonArray);
        }
        return resultObjectBuilder.build();
    }

    private static JsonArray getResultsForRule(Document htmlDocument, Rule rule) {
        JsonArrayBuilder ruleArrayBuilder = Json.createArrayBuilder();
        List<String> results = getResultForRule(rule, htmlDocument);
        for (String result : results){
            ruleArrayBuilder.add(result);
        }
        return ruleArrayBuilder.build();
    }

    private static List<String> getResultForRule(Rule rule, Document htmlDocument) {
        List<String> results = new LinkedList<>();
        String result;
        for (Element element : htmlDocument.select(rule.getXPath())){
            result = getResultFromElement(rule, element);
            if (isResultValid(result)){
                results.add(result);
            }
        }
        return results;
    }

    private static String getResultFromElement(Rule rule, Element element) {
        if (isAttributeSet(rule)){
            return element.attr(rule.getAttribute());
        }
        if (rule.isResultAsPlainText()) {
            return element.text();
        }
        return element.html();
    }

    private static JsonObject readRulesJsonFromFile(String filePath) throws FileNotFoundException {
        return readRulesJson(Json.createReader(new FileInputStream(filePath)));
    }

    private static JsonObject readRulesJson(String ruleAsJson) {
        return readRulesJson(Json.createReader(new StringReader(ruleAsJson)));
    }

    private static JsonObject readRulesJson(JsonReader reader){
        JsonObject rule = reader.readObject();
        reader.close();
        return rule;
    }

    //conditionals
    private static boolean isAttributeSet(Rule rule){
        return rule.getAttribute() != null;
    }

    private static boolean isResultValid(String result){
        return result != null && result.length() > 0;
    }

}
