package de.hpi.parser.model.data;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OfferRepositoryImpl implements OfferRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    //initialization
    @Autowired
    public OfferRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }


    @Override
    public List<Offer> getFirstOffersOfShop(long shopID, int maxOffers) {
        List<Offer> offers = new LinkedList<>();
        DBCollection collection = getCollectionByShopID(shopID);
        if (collection != null){
            DBCursor cursor = collection.find().limit(maxOffers);
            while (cursor.hasNext()){
                offers.add(convertDBObjectToOffer(cursor.next()));
            }
        }
        return offers;
    }

    //actions
    private DBCollection getCollectionByShopID(long shopID){
        DBCollection collection = null;
        for (String collectionName : getMongoTemplate().getCollectionNames()){
            collection = getMongoTemplate().getCollection(collectionName);
            if (getFirstOffer(collection).getShopId().longValue() == shopID){
                return collection;
            }
        }
        return null;
    }

    private Offer getFirstOffer(DBCollection collection){
        return convertDBObjectToOffer(collection.findOne());
    }

    //conversion
    private Offer convertDBObjectToOffer(DBObject offerDbObject){
        return getMongoTemplate().getConverter().read(Offer.class, offerDbObject);
    }
}
