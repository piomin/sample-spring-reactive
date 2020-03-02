package pl.piomin.services.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@SpringBootApplication
public class Application {

	@Value("${mongodb.host:192.168.99.100}")
	private String mongoHost;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public @Bean MongoClient mongoClient() {
		return MongoClients.create("mongodb://"  + mongoHost);
	}

	public @Bean ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient(), "test");
	}

	public @Bean WebClient webClient() {
		return WebClient.builder().clientConnector(new ReactorClientHttpConnector()).baseUrl("http://localhost:2222").build();
	}

}
