package de.hpi.parser.respository;

public interface ParsedOfferRepository {

    void save(long shopId, String offerJsonString);
}
