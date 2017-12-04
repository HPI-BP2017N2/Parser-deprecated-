package de.hpi.parser.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.json.JsonObject;

public class Rule {

    @Getter(AccessLevel.PRIVATE) private static final String X_PATH_KEY = "x-path", ATTRIBUTE_KEY = "attribute";

    @Getter @Setter(AccessLevel.PRIVATE) private String xPath, attribute;

    private Rule(String xPath, String attribute) {
        setXPath(xPath);
        setAttribute(attribute);
    }

    public static Rule parseRule(JsonObject ruleJsonObject){
        return new Rule(ruleJsonObject.getString(
                getX_PATH_KEY()),
                ruleJsonObject.getString(getATTRIBUTE_KEY(), null));
    }

}
