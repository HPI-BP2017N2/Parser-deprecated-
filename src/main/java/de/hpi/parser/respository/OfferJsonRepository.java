package de.hpi.parser.respository;

import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface OfferJsonRepository extends Repository<String, Long> {

    public void save(String offerJsonString);
}
