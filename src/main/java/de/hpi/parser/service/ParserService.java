package de.hpi.parser.service;

import de.hpi.parser.model.Parser;
import de.hpi.parser.properties.ParserProperties;
import de.hpi.parser.respository.ParsedOfferRepository;
import de.hpi.restclient.clients.ShopRulesGeneratorClient;
import de.hpi.restclient.dto.GetRulesResponse;
import de.hpi.restclient.pojo.ExtractedDataEntry;
import de.hpi.restclient.pojo.ExtractedDataMap;
import de.hpi.restclient.pojo.OfferAttribute;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void parseOffer(String html, long shopID , String url) {
        GetRulesResponse response = getShopRulesGeneratorClient().getRules(shopID);
        ExtractedDataMap extractedDataMap = Parser.parse(html, response.getRules());

        ExtractedDataEntry urlEntry = new ExtractedDataEntry();
        urlEntry.setValue(url);
        extractedDataMap.getData().put(OfferAttribute.URL, urlEntry);
        getRepository().save(shopID, extractedDataMap);
    }

}
