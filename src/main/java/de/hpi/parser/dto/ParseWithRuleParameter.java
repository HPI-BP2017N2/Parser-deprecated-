package de.hpi.parser.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ParseWithRuleParameter {

    private String html;
    private String ruleAsJson;

}
