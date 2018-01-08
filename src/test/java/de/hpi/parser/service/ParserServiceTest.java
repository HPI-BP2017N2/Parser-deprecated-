package de.hpi.parser.service;

import de.hpi.parser.model.JsonConverter;
import de.hpi.parser.model.Parser;
import de.hpi.parser.model.data.Rule;
import de.hpi.parser.model.data.TmpRules;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserServiceTest {

    @Test
    void parseHtmlWithSpecifiedRule() throws IOException {
        String html = readFileFromClasspath("parseWithRuleAsJson/htmlTestFile.htm");
        HashMap<String, List<Rule>> rules = JsonConverter.readJavaObjectFromInputStream(getClass().getClassLoader()
                .getResourceAsStream("parseWithRuleAsJson/rules.json"), TmpRules.class).getRules();
        String expectedResult = readFileFromClasspath("parseWithRuleAsJson/expectedResult.json");

        String result = JsonConverter.getJsonStringForJavaObject(Parser.parse(html, rules));
        assertTrue(compareJson(expectedResult, result));
    }

    private boolean compareJson(String jsonA, String jsonB){
        JsonObject objectA = readJson(jsonA);
        JsonObject objectB = readJson(jsonB);
        return objectA.toString().equals(objectB.toString());
    }

    private JsonObject readJson(String jsonString) {
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject rule = reader.readObject();
        reader.close();
        return rule;
    }

    private String readFileFromClasspath(String classFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(classFilePath)));
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }
}