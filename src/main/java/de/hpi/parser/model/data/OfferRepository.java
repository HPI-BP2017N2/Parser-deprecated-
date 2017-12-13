package de.hpi.parser.model.data;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository {

    public List<Offer> getFirstOffersOfShop(long shopID, int maxOffers);

}
