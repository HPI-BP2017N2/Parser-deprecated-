package de.hpi.parser.config;

import de.hpi.parser.config.AbstractMongoConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@ConfigurationProperties(prefix = "parsedOffers.mongodb")
public class ParsedOfferMongoConfig extends AbstractMongoConfig {

    @Primary
    @Override
    public @Bean
    MongoTemplate getMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}