package account.spring.hardware.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "account.spring.hardware.repository",
        mongoTemplateRef = "mongoTemplate"
)
public class HardwareConfig {

    @Value("${MONGO_URI}")
    private String uri;

    @Value("${MONGO_DBNAME}")
    private String dbName;

    @Value("${MONGO_USERNAME}")
    private String username;

    @Value("${MONGO_PASSWORD}")
    private char[] password;

    @Bean
    @Primary
    public MongoProperties mongoProperties() {
        MongoProperties properties = new MongoProperties();

        properties.setUri(uri);
        properties.setDatabase(dbName);
        properties.setUsername(username);
        properties.setPassword(password);

        return properties;
    }

    @Bean
    public MongoClient mongoClient(MongoProperties mongoProperties) {
        return MongoClients.create(mongoProperties.getUri());
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient, MongoProperties mongoProperties) {
        return new MongoTemplate(mongoClient, dbName);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient mongoClient, MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, dbName);
    }
}