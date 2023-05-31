package pl.piomin.services.customer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pl.piomin.services.customer.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

    Mono<Customer> findByPesel(String pesel);

}
