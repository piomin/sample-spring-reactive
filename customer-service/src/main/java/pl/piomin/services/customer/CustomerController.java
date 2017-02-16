package pl.piomin.services.customer;

import java.util.logging.Logger;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import pl.piomin.services.common.Account;
import pl.piomin.services.common.Customer;
import pl.piomin.services.customer.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController {

	private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

	@Autowired
	private CustomerRepository repository;
	@Autowired
	private WebClient webClient;

	@GetMapping(value = "/customer/{id}")
	public Mono<Customer> findById(@PathVariable("id") String id) {
		return repository.findById(id)
				.map(c -> new Customer(c.getId(), c.getFirstName(), c.getLastName(), c.getPesel()));
	}

	@GetMapping(value = "/customer")
	public Flux<Customer> findAll() {
		return repository.findAll().map(c -> new Customer(c.getId(), c.getFirstName(), c.getLastName(), c.getPesel()));
	}

	@GetMapping(value = "/customer/accounts/{pesel}")
	public Mono<Customer> findByPeselWithAccounts(@PathVariable("pesel") String pesel) {
		return repository.findByPesel(pesel).log().flatMap(customer -> webClient.get().uri("/account/customer/{customer}", customer.getId()).accept(MediaType.APPLICATION_JSON)
				.exchange().log().flatMap(response -> response.bodyToFlux(Account.class))).collectList().map(l -> {return new Customer(pesel, l);});
	}

	@PostMapping(value = "/customer")
	public Mono<Customer> create(@RequestBody Publisher<Customer> customerStream) {
		logger.info("Create customer");
		return repository.save(Mono.from(customerStream)
						.map(a -> new pl.piomin.services.customer.model.Customer(a.getFirstName(), a.getLastName(),
								a.getPesel())))
				.map(a -> new Customer(a.getId(), a.getFirstName(), a.getLastName(), a.getPesel()));
	}
}
