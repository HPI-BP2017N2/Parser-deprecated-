package de.hpi.parser.respository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import de.hpi.restclient.dto.ParsedOffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ParsedOfferRepositoryImpl implements ParsedOfferRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    // initialization
    @Autowired
    public ParsedOfferRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }

    // convenience
    @Override
    public void save(long shopId, String offerJsonString) {

    }

    @Override
    public void save(long shopId, ParsedOffer offer) {
        if(!collectionExists(shopId)) {
            createCollection(shopId);
        }
        DBCollection collection = getMongoTemplate().getCollection(Long.toString(shopId));
        collection.save(convertParsedOfferToDbObject(offer));
    }

    // conversion
    private DBObject convertParsedOfferToDbObject(ParsedOffer parsedOffer){
        DBObject dbObject = new BasicDBObject();
        getMongoTemplate().getConverter().write(parsedOffer, dbObject);
        return dbObject;
    }

    // conditional
    private boolean collectionExists(long shopId) {
        Set<String> collections = getMongoTemplate().getCollectionNames();
        return collections.contains(Long.toString(shopId));
    }

    // actions
    private void createCollection(long shopId) {
        getMongoTemplate().createCollection(Long.toString(shopId));
    }
}