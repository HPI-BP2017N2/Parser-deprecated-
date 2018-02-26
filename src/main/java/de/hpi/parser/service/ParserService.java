package de.hpi.parser.service;

import de.hpi.parser.model.Parser;
import de.hpi.parser.properties.ParserProperties;
import de.hpi.parser.respository.ParsedOfferRepository;
import de.hpi.restclient.clients.BPBridgeClient;
import de.hpi.restclient.clients.ShopRulesGeneratorClient;
import de.hpi.restclient.dto.GetRulesResponse;
import de.hpi.restclient.dto.ParsedOffer;
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

    private ParsedOfferRepository repository;
    private ParserProperties properties;
    private ShopRulesGeneratorClient shopRulesGeneratorClient;

    @Autowired
    public ParserService(ParserProperties properties,
                         ShopRulesGeneratorClient shopRulesGeneratorClient,
                         ParsedOfferRepository repository) {
        setProperties(properties);
        setShopRulesGeneratorClient(shopRulesGeneratorClient);
        setRepository(repository);
    }

    public void parseOffer(String html, long shopID) {
        GetRulesResponse response = getShopRulesGeneratorClient().getRules(shopID);
        ExtractedDataMap extractedDataMap = Parser.parse(html, response.getRules());

        getRepository().save(shopID, extractedDataMap);
    }

}
