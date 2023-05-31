package pl.piomin.services.account;

import java.util.logging.Logger;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.account.repository.AccountRepository;
import pl.piomin.services.common.Account;
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
        return repository.findByCustomerId(customerId)
                .map(a -> new Account(a.getId(), a.getCustomerId(), a.getNumber(), a.getAmount()));
    }

    @GetMapping(value = "/account")
    public Flux<Account> findAll() {
        return repository.findAll().map(a -> new Account(a.getId(), a.getCustomerId(), a.getNumber(), a.getAmount()));
    }

    @GetMapping(value = "/account/{id}")
    public Mono<Account> findById(@PathVariable("id") Integer id) {
        return repository.findById(id)
                .map(a -> new Account(a.getId(), a.getCustomerId(), a.getNumber(), a.getAmount()));
    }

    @PostMapping(value = "/account")
    public Mono<Account> create(@RequestBody Publisher<Account> accountStream) {
        return repository
                .save(Mono.from(accountStream)
                        .map(a -> new pl.piomin.services.account.model.Account(a.getNumber(), a.getCustomerId(),
                                a.getAmount())))
                .map(a -> new Account(a.getId(), a.getCustomerId(), a.getNumber(), a.getAmount()));
    }

}
