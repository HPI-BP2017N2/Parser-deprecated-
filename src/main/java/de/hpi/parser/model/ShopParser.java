package de.hpi.parser.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class ShopParser {

    //convenience


    //actions
    private List<String> getXPathsForOfferAttribute(Document html, String offerAttribute){
        List<String> xPaths = new LinkedList<>();
        for (Element element : html.select("*:containsOwn(" + offerAttribute + ")")){
            xPaths.add(getXPathForDomElement(html, element));
        }
        return xPaths;
    }

    private String getXPathForDomElement(Document html, Element element){
        StringBuilder xPathBuilder = new StringBuilder();
        while (!isElementIDSet(element)){
            int tagIndex = getTagIndexForChild(element.parent(), element);
            xPathBuilder.insert(0, " " + element.tagName() + ":eq(" + tagIndex + ")");
            element = element.parent();
        }
        xPathBuilder.insert(0,"*#" + element.id());
        return xPathBuilder.toString();
    }

    private int getTagIndexForChild(Element parent, Element child){
        Elements childElementsWithTag = parent.getElementsByTag(child.tagName());
        for (int iElement = 0; iElement < childElementsWithTag.size(); iElement++){
            if (childElementsWithTag.get(iElement).equals(child)){
                return iElement;
            }
        }
        return -1;
    }

    //conditionals
    private boolean isElementIDSet(Element element) {
        return !element.id().isEmpty();
    }

}
