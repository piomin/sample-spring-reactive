package pl.piomin.services.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.piomin.services.common.Account;
import pl.piomin.services.customer.model.Customer;
import pl.piomin.services.customer.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
public class CustomerController {

    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/customer/{id}")
    public Mono<Customer> findById(@PathVariable("id") String id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/customer")
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    @GetMapping(value = "/customer/accounts/{pesel}")
    public Mono<pl.piomin.services.common.Customer> findByPeselWithAccounts(@PathVariable("pesel") String pesel) {
        return repository.findByPesel(pesel).log()
                .flatMap(customer -> webClient.get()
                        .uri("/account/customer/{customer}", customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange().log()
                        .flatMap(response -> response.bodyToFlux(Account.class)
                                .collectList()
                                .map(l -> new pl.piomin.services.common.Customer(pesel, l))));
    }

    @PostMapping(value = "/customer")
    public Mono<Customer> create(@RequestBody Customer customer) {
        logger.info("Create customer");
        return repository.save(customer);
    }
}
