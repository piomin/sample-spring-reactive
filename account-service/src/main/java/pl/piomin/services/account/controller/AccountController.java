package pl.piomin.services.account.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.account.model.Account;
import pl.piomin.services.account.repository.AccountRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class.getName());

    @Autowired
    private AccountRepository repository;

    @GetMapping(value = "/account/customer/{customer}")
    public Flux<Account> findByCustomer(@PathVariable("customer") String customerId) {
        logger.info("findByCustomer: " + customerId);
        return repository.findByCustomerId(customerId);
    }

    @GetMapping(value = "/account")
    public Flux<Account> findAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/account/{id}")
    public Mono<Account> findById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @PostMapping(value = "/account")
    public Mono<Account> create(@RequestBody Account account) {
        return repository.save(account);
    }

}
