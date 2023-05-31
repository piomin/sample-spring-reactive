package pl.piomin.services.customer;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;

@TestConfiguration
public class MongoContainerDevMode {

    @Bean
    @ServiceConnection
    public MongoDBContainer mongodb () {
        return new MongoDBContainer("mongo:5.0");
    }

}
