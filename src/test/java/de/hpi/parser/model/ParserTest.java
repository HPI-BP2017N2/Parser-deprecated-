package de.hpi.parser.model;

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

class ParserTest {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private static Parser parser;

    @BeforeAll
    static void setUp() {
        setParser(new Parser());
    }

    @Test
    void parseHtmlWithRuleAsJson() throws IOException {
        String html = readFileFromClasspath("htmlTestFile.htm");
        String rules = readFileFromClasspath("rules.json");
        String expectedResult = readFileFromClasspath("expectedResult.json");


        String result = ParserTest.getParser().parseHtmlWithRuleAsJson(html, rules);
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