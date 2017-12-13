package de.hpi.parser.model.data;

import com.mongodb.DBCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OfferRepositoryImpl implements OfferRepository {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MongoTemplate mongoTemplate;

    @Autowired
    public OfferRepositoryImpl(MongoTemplate mongoTemplate) {
        setMongoTemplate(mongoTemplate);
    }

    @Override
    public long offerCount() {
        long count = 0L;
        for (String collection : getMongoTemplate().getCollectionNames()){
            count += getMongoTemplate().getCollection(collection).count();
        }
        return count;
    }
}
