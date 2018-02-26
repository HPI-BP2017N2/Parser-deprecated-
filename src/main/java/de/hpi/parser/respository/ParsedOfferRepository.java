package de.hpi.parser.respository;

import de.hpi.restclient.pojo.ExtractedDataMap;

public interface ParsedOfferRepository {

    void save(long shopId, ExtractedDataMap offer);
}
