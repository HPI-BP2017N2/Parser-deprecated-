package de.hpi.parser.service;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserServiceTest {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static ParserService parserService;

    @BeforeAll
    static void setUp() {
        setParserService(new ParserService());
    }

    @Test
    void parseHtmlWithSpecifiedRule() throws IOException {
        String html = readFileFromClasspath("parseWithRuleAsJson/htmlTestFile.htm");
        String rules = readFileFromClasspath("parseWithRuleAsJson/rules.json");
        String expectedResult = readFileFromClasspath("parseWithRuleAsJson/expectedResult.json");

        String result = ParserServiceTest.getParserService().parseHtmlWithSpecifiedRule(html, rules);
        assertTrue(compareJson(expectedResult, result));
    }

    @Test
    void extractJsonLdFromHtml() throws IOException {
        String html = readFileFromClasspath("extractJsonLdFromHtml/htmlTestFile.htm");
        String expectedResult = readFileFromClasspath("extractJsonLdFromHtml/expectedResult.json");

        String result = ParserServiceTest.getParserService().extractJsonLdFromHtml(html);
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