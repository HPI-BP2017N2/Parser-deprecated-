package de.hpi.parser.service;

import de.hpi.parser.model.Parser;
import de.hpi.parser.properties.ParserProperties;
import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.clients.ShopRulesGeneratorClient;
import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.Rules;
import de.hpi.restclient.properties.BPBridgeProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Service
public class ParserService {

    private ParserProperties properties;
    private ShopRulesGeneratorClient shopRulesGeneratorClient;

    @Autowired
    public ParserService(ParserProperties properties, ShopRulesGeneratorClient shopRulesGeneratorClient) {
        setProperties(properties);
        setShopRulesGeneratorClient(shopRulesGeneratorClient);
    }

    public void parseOffer(String html, long shopID) {
        getShopRulesGeneratorClient().getRules(shopID, getProperties().getRoot(), getProperties().getRulesCallbackRoute());
    }

    public void handleRulesResponse(Rules rules, long shopID) {
        /*
        TODO
        Write store result in Parser DB
        Use rabbit
         */
    }
}
