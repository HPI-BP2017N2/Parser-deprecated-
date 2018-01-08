package de.hpi.parser.model.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class TmpRules {

    private HashMap<String, List<Rule>> rules;
}
