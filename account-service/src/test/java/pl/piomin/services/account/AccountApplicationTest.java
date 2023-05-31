package pl.piomin.services.account;

import org.springframework.boot.SpringApplication;

public class AccountApplicationTest {

    public static void main(String[] args) {
        SpringApplication.from(AccountApplication::main)
                .with(MongoContainerDevMode.class)
                .run(args);
    }

}
