package de.hpi.parser.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Parser {

    public String parseHtmlWithRuleAsJson(String html, String ruleAsJson) throws IOException {
        JsonObject rulesJsonObject = readRulesJson(ruleAsJson);
        Document htmlDocument = Jsoup.parse(html);
        JsonObject dataJsonObject = extractData(rulesJsonObject, htmlDocument);
        return jsonToString(dataJsonObject);
    }

    private static String jsonToString(JsonObject dataJsonObject) throws IOException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter writer = writerFactory.createWriter(stringWriter);
        writer.writeObject(dataJsonObject);
        writer.close();
        String jsonString = stringWriter.toString();
        stringWriter.close();
        return jsonString;
    }

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
    private static boolean isAttributeSet(Rule rule){
        return rule.getAttribute() != null;
    }

    private JsonObject readRulesJson(String ruleAsJson) {
        JsonReader reader = Json.createReader(new StringReader(ruleAsJson));
        JsonObject rule = reader.readObject();
        reader.close();
        return rule;
    }

}
