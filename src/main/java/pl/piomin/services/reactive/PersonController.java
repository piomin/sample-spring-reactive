package pl.piomin.services.reactive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.reactive.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonController {

	private List<Person> persons;

	public PersonController() {
		persons = new ArrayList<>();
		persons.add(new Person(1, "Piotr", "Minkowski"));
		persons.add(new Person(2, "Jan", "Kowalski"));
		persons.add(new Person(3, "Iwona", "Malinowska"));
	}

	@RequestMapping(value = "/person/{id}")
	public Mono<Person> getPerson(@PathVariable("id") Integer id) {
		return Mono.just(persons.stream().filter(p -> p.getId().intValue() == id.intValue()).findFirst().get());
	}

	@RequestMapping(value = "/person")
	public Flux<Person> getPersons() {
		return Flux.fromStream(persons.stream());
	}
}
