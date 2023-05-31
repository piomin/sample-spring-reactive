package pl.piomin.services.customer.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;

import pl.piomin.services.customer.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepository {

    @Autowired
    private ReactiveMongoTemplate template;

    public Mono<Customer> findById(String id) {
        return template.findById(id, Customer.class);
    }

    public Mono<Customer> findByPesel(String pesel) {
        return template.findOne(query(where("pesel").is(pesel)), Customer.class);
    }

    public Flux<Customer> findAll() {
        return template.findAll(Customer.class);
    }

    public Mono<Customer> save(Mono<Customer> customer) {
        return template.insert(customer);
    }

}
