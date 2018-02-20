package de.hpi.parser.respository;

import de.hpi.restclient.dto.ParsedOffer;

public interface ParsedOfferRepository {

    void save(long shopId, String offerJsonString);

    void save(long shopId, ParsedOffer offer);
}
