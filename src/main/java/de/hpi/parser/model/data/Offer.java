package de.hpi.parser.model.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class Offer {

    private List<String> categoryPaths;
    private Map<String, Number> price;
    private Map<String, String> description, offerTitle, url;
    private Number shopId;
    private String currency, sku, han, brandSearchtext, categoryString, ean, attrSearchtext, productSearchtext;

}
