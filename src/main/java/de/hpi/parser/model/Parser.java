package de.hpi.parser.model;

import lombok.AccessLevel;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.json.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public class Parser {

    //constants
    @Getter(AccessLevel.PRIVATE) private static final String JSON_LD_SELECT = "script[type='application/ld+json']";

    //convenience
    public String parseHtmlWithRuleAsJson(String html, String ruleAsJson) throws IOException {
        JsonObject rulesJsonObject = readRulesJson(ruleAsJson);
        Document htmlDocument = Jsoup.parse(html);
        JsonObject dataJsonObject = extractData(rulesJsonObject, htmlDocument);
        return dataJsonObject.toString();
    }

    public String extractJsonLdFromHtml(String html){
        Document htmlDocument = Jsoup.parse(html);
        List<String> jsonStrings = new LinkedList<>();
        for (Element element : htmlDocument.select(getJSON_LD_SELECT())) {
            jsonStrings.add(element.html());
        }

        JsonObject result = getJsonFromList(jsonStrings);
        return result.toString();
    }

    private JsonObject getJsonFromList(List<String> jsonStrings) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (String jsonString : jsonStrings){
            arrayBuilder.add(jsonString);
        }
        objectBuilder.add("jsonLd", arrayBuilder.build());
        return objectBuilder.build();
    }

    //actions
    private JsonObject extractData(JsonObject rulesJsonObject, Document htmlDocument) {
        JsonObjectBuilder dataObjectBuilder = Json.createObjectBuilder();
        Rule rule;
        JsonArrayBuilder ruleArrayBuilder;
        JsonObjectBuilder resultObjectBuilder;
        for (String key : rulesJsonObject.keySet()){
            resultObjectBuilder = Json.createObjectBuilder();
            List<JsonObject> jsonRules = rulesJsonObject.getJsonArray(key).getValuesAs(JsonObject.class);
            for (int iJsonRule = 0; iJsonRule < jsonRules.size(); iJsonRule++) {
                ruleArrayBuilder = Json.createArrayBuilder();
                rule = Rule.parseRule(jsonRules.get(iJsonRule));
                List<String> results = getResultForRule(rule, htmlDocument);
                if (!results.isEmpty()){
                    for (String result : results){
                        ruleArrayBuilder.add(result);
                    }
                    resultObjectBuilder.add(Integer.toString(iJsonRule), ruleArrayBuilder.build());
                }

            }
            dataObjectBuilder.add(key, resultObjectBuilder.build());
        }
        return dataObjectBuilder.build();
    }

    private static List<String> getResultForRule(Rule rule, Document htmlDocument) {
        List<String> results = new LinkedList<>();
        String result;
        for (Element element : htmlDocument.select(rule.getXPath())){
            result = isAttributeSet(rule) ? element.attr(rule.getAttribute()) : element.text();
            if (result != null && result.length() > 0){
                results.add(result);
            }
        }
        return results;
    }

    private JsonObject readRulesJson(String ruleAsJson) {
        JsonReader reader = Json.createReader(new StringReader(ruleAsJson));
        JsonObject rule = reader.readObject();
        reader.close();
        return rule;
    }

    //conditionals
    private static boolean isAttributeSet(Rule rule){
        return rule.getAttribute() != null;
    }

}
