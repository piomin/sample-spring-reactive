package pl.piomin.services.reactive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.reactive.model.Account;
import reactor.core.publisher.Flux;

@RestController
public class AccountController {

	private List<Account> accounts;

	public AccountController() {
		accounts = new ArrayList<>();
		accounts.add(new Account(1, 1, "0123456789", 2000));
		accounts.add(new Account(2, 1, "0123456780", 5000));
		accounts.add(new Account(3, 2, "0123456781", 1000));
		accounts.add(new Account(4, 2, "0123456782", 1500));
		accounts.add(new Account(5, 3, "0123456783", 2000));
		accounts.add(new Account(6, 3, "0123456784", 3000));
	}

	@RequestMapping(value = "/account/customer/{customer}")
	public Flux<Account> findByCustomer(@PathVariable("customer") Integer id) {
		return Flux.fromStream(accounts.stream().filter(a -> a.getCustomerId().intValue() == id.intValue()));
	}

}
