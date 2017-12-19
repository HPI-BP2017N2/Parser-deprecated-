package de.hpi.parser.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Rule {

    private boolean resultAsPlainText;
    private String xPath, attribute;

}
