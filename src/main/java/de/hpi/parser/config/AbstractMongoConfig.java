package de.hpi.parser.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;

@Getter(AccessLevel.PRIVATE) @Setter
public abstract class AbstractMongoConfig {

    String host, database, username;
    char[] password;
    int port;

    // convenience
    public MongoDbFactory mongoDbFactory() throws Exception {
        ServerAddress serverAddress = new ServerAddress(getHost(), getPort());
        MongoCredential credential = MongoCredential.createCredential(getUsername(), getDatabase(), getPassword());
        return new SimpleMongoDbFactory(new MongoClient(serverAddress, Arrays.asList(credential)), getDatabase());
    }

    public abstract MongoTemplate getMongoTemplate() throws Exception;
}