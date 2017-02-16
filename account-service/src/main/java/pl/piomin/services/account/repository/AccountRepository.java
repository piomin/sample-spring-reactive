package pl.piomin.services.account.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.stereotype.Repository;

import pl.piomin.services.account.model.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AccountRepository {

	@Autowired
	private ReactiveMongoTemplate template;

	public Mono<Account> findById(Integer id) {
		return template.findById(id, Account.class);
	}

	public Flux<Account> findAll() {
		return template.findAll(Account.class);
	}

	public Flux<Account> findByCustomerId(String customerId) {
		return template.find(query(where("customerId").is(customerId)), Account.class);
	}

	public Mono<Account> save(Mono<Account> account) {
		return template.insert(account);
	}
	
}
