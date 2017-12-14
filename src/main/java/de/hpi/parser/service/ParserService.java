package de.hpi.parser.service;

import de.hpi.parser.model.JsonReader;
import de.hpi.parser.model.Parser;
import de.hpi.parser.model.data.RuleType;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import java.util.HashMap;

@Service
public class ParserService {

    //constants
    @Getter(AccessLevel.PRIVATE) private static final HashMap<RuleType, JsonObject> STORED_RULES = loadStoredRules();

    //initialize
    private static HashMap<RuleType, JsonObject> loadStoredRules() {
        String rulesDir = "rules", jsonFileEnding = ".json";

        HashMap<RuleType, JsonObject> ruleNameMap = new HashMap<>();
        String rulePath;
        for (RuleType type : RuleType.values()){
            rulePath = rulesDir + "/" + type.toString().toLowerCase() + jsonFileEnding;
            ruleNameMap.put(type, loadFromClasspath(rulePath));
        }
        return ruleNameMap;
    }

    private static JsonObject loadFromClasspath(String path){
        return JsonReader.readJsonObject(ParserService.class.getClassLoader().getResourceAsStream(path));
    }

    public String parseHtmlWithJsonLD(String html) {
        return Parser.parse(html, getSTORED_RULES().get(RuleType.JSON_LD));
    }

    public String parseHtmlWithSchemaOrg(String html) {
        return Parser.parse(html, getSTORED_RULES().get(RuleType.SCHEMA_ORG));
    }



}
