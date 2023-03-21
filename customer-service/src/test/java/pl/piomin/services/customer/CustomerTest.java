package pl.piomin.services.customer;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pl.piomin.services.common.Customer;

import java.util.logging.Logger;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerTest {

	private static final Logger logger = Logger.getLogger("CustomerTest");

	private WebClient webClient;

//	@LocalServerPort
	private int port;

//	@Before
	public void setup() {
		this.webClient = WebClient.create("http://localhost:" + this.port);
	}

//	@Test
	public void getCustomerAccounts() {
		Customer customer = this.webClient.get().uri("/customer/accounts/234543647565")
				.accept(MediaType.APPLICATION_JSON).exchange()
				.map(res -> res.body(BodyExtractors.toMono(Customer.class)))
				.block().block();
		logger.info("Customer: " + customer);
	}

//	@Test
	public void addCustomer() {
		Customer customer = new Customer(null, "Adam", "Kowalski", "123456787654");
		customer = webClient.post().uri("/customer").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(customer))
				.exchange().map(response -> response.bodyToMono(Customer.class))
				.block().block();
		logger.info("Customer: " + customer);
	}
	
}
