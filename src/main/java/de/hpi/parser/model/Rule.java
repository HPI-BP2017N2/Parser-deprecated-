package de.hpi.parser.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.json.JsonObject;

public class Rule {

    //constants
    @Getter(AccessLevel.PRIVATE) private static final boolean DEFAULT_RESULT_AS_PLAIN_TEXT = true;
    @Getter(AccessLevel.PRIVATE) private static final String X_PATH_KEY = "x-path", ATTRIBUTE_KEY = "attribute",
            RESULT_AS_PLAIN_TEXT_KEY = "result-as-plain-text";
    @Getter(AccessLevel.PRIVATE) private static final String DEFAULT_ATTRIBUTE = null;

    @Getter @Setter(AccessLevel.PRIVATE) private boolean resultAsPlainText;
    @Getter @Setter(AccessLevel.PRIVATE) private String xPath, attribute;

    private Rule(String xPath, String attribute, boolean resultAsPlainText) {
        setXPath(xPath);
        setAttribute(attribute);
        setResultAsPlainText(resultAsPlainText);
    }

    public static Rule parseRule(JsonObject ruleJsonObject){
        return new Rule(ruleJsonObject.getString(
                getX_PATH_KEY()),
                ruleJsonObject.getString(getATTRIBUTE_KEY(), getDEFAULT_ATTRIBUTE()),
                ruleJsonObject.getBoolean(getRESULT_AS_PLAIN_TEXT_KEY(), isDEFAULT_RESULT_AS_PLAIN_TEXT()));
    }

}
