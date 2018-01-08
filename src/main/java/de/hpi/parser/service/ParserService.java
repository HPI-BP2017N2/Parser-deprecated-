package de.hpi.parser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.hpi.parser.model.JsonConverter;
import de.hpi.parser.model.Parser;
import de.hpi.parser.model.data.Rule;
import de.hpi.parser.model.data.RuleType;
import de.hpi.parser.model.data.TmpRules;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class ParserService {

    //constants
    @Getter(AccessLevel.PRIVATE) private static final HashMap<RuleType, HashMap<String, List<Rule>>> STORED_RULES =
            loadStoredRules();

    //initialize
    private static HashMap<RuleType, HashMap<String, List<Rule>>> loadStoredRules() {
        String rulesDir = "rules", jsonFileEnding = ".json";

        HashMap<RuleType, HashMap<String, List<Rule>>> ruleNameMap = new HashMap<>();
        String rulePath;
        for (RuleType type : RuleType.values()){
            rulePath = rulesDir + "/" + type.toString().toLowerCase() + jsonFileEnding;
            try {
                ruleNameMap.put(type, loadFromClasspath(rulePath));
            } catch (IOException ignored) {}
        }
        return ruleNameMap;
    }

    private static HashMap<String, List<Rule>> loadFromClasspath(String path) throws IOException {
        /*
        Workaround: since we cant do HashMap<String, List<Rules>>.class we have introduced TmpRules
         */
        return JsonConverter.readJavaObjectFromInputStream(ParserService.class
                .getClassLoader().getResourceAsStream(path), TmpRules.class).getRules();
    }

    public String parseHtmlWithJsonLD(String html) throws JsonProcessingException {
        return JsonConverter.getJsonStringForJavaObject(Parser.parse(html, getSTORED_RULES().get(RuleType.JSON_LD)));
    }

    public String parseHtmlWithSchemaOrg(String html) throws JsonProcessingException {
        return JsonConverter.getJsonStringForJavaObject(Parser.parse(html, getSTORED_RULES().get(RuleType.SCHEMA_ORG)));
    }



}
