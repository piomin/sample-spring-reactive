package pl.piomin.services.reactive;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import pl.piomin.services.reactive.model.Account;
import pl.piomin.services.reactive.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonTest {

	private static final Logger logger = Logger.getLogger("PersonTest");
	
	private WebClient webClient;

	@LocalServerPort
	private int port;

	@Before
	public void setup() {
		this.webClient = WebClient.create("http://localhost:" + this.port);
	}

	@Test
	public void getPersonTest() {
		Mono<Person> result1 = this.webClient.get().uri("/person/1").accept(MediaType.APPLICATION_JSON).exchange()
				.then(response -> response.bodyToMono(Person.class));

		Flux<Account> result2 = this.webClient.get().uri("/account/customer/1").accept(MediaType.APPLICATION_JSON)
				.exchange().flatMap(response -> response.bodyToFlux(Account.class));

//		result1.log().subscribeOn(Schedulers.newParallel("ex1")).publishOn(Schedulers.newParallel("ex2", 2))
//				.subscribe(value -> {
//					System.out.println(value);
//				});

		result2.log().subscribeOn(Schedulers.newParallel("sub")).publishOn(Schedulers.newParallel("pub", 2))
				.subscribe(value -> {
					logger.info(value.toString());
				});

		// Flux f = Flux.merge(result2, result1);
		// f.log().subscribeOn(Schedulers.newParallel("ex1")).subscribe(value ->
		// {System.out.println(value);});
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
