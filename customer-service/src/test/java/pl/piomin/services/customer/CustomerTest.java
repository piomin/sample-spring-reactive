package pl.piomin.services.customer;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import pl.piomin.services.common.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerTest {

	private static final Logger logger = Logger.getLogger("CustomerTest");

	private WebClient webClient;

	@LocalServerPort
	private int port;

	@Before
	public void setup() {
		this.webClient = WebClient.create("http://localhost:" + this.port);
	}

	@Test
	public void getCustomerAccounts() {
		Customer customer = this.webClient.get().uri("/customer/accounts/234543647565")
				.accept(MediaType.APPLICATION_JSON).exchange().then(response -> response.bodyToMono(Customer.class))
				.block();
		logger.info("Customer: " + customer);
	}

	@Test
	public void addCustomer() {
		Customer customer = new Customer(null, "Adam", "Kowalski", "123456787654");
		customer = webClient.post().uri("/customer").accept(MediaType.APPLICATION_JSON)
				.exchange(BodyInserters.fromObject(customer)).then(response -> response.bodyToMono(Customer.class))
				.block();
		logger.info("Customer: " + customer);
	}
	
}
