package de.hpi.parser.model;

import de.hpi.restclient.pojo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class Parser {

    //convenience
    public static ExtractedDataMap parse(String html, Rules rules){
        return parse(Jsoup.parse(html), rules);
    }

    private static ExtractedDataMap parse(Document html, Rules rules){
        EnumMap<OfferAttribute, ExtractedDataEntry> data = new EnumMap<>(OfferAttribute.class);
        for (Map.Entry<OfferAttribute, Rule> entry : rules.getRules().entrySet()){
            data.put(entry.getKey(), getExtractedDataEntry(html, entry.getValue()));
        }
        ExtractedDataMap map = new ExtractedDataMap();
        map.setData(data);
        return map;
    }

    private static ExtractedDataEntry getExtractedDataEntry(Document html, Rule rule) {
        for (RuleEntry entry : rule.getEntries()) {
            for (Element element : html.select(entry.getSelector())){
                String extractedData = extract(entry, element);
                if (isResultValid(extractedData)){
                    ExtractedDataEntry result = new ExtractedDataEntry();
                    result.setValue(extractedData);
                    return result;
                }
            }
        }
        return null;
    }

    private static String extract(RuleEntry entry, Element element) {
        if (isAttributeSet(entry)){
            return element.attr(entry.getAttribute());
        }
        if (entry.isResultAsPlainText()) {
            return element.text();
        }
        return element.html();
    }

    //conditionals
    private static boolean isAttributeSet(RuleEntry entry){
        return entry.getAttribute() != null;
    }

    private static boolean isResultValid(String result){
        return result != null && result.length() > 0;
    }

}
