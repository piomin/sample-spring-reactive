package pl.piomin.services.customer;

import org.springframework.boot.SpringApplication;

public class CustomerApplicationTest {

    public static void main(String[] args) {
        SpringApplication.from(CustomerApplication::main)
                .with(MongoContainerDevMode.class)
                .run(args);
    }

}
