package pl.piomin.services.customer;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.services.customer.model.Customer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTests {

    static String id;

    @Container
    @ServiceConnection
    static MongoDBContainer mongodb = new MongoDBContainer("mongo:5.0");

//    @DynamicPropertySource
//    static void registerMongoProperties(DynamicPropertyRegistry registry) {
//        String uri = mongodb.getConnectionString() + "/test";
//        registry.add("spring.data.mongodb.uri", () -> uri);
//    }

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void add() {
        Customer customer = new Customer("Test1", "Test2", "14324230");
        customer = restTemplate.postForObject("/customer", customer, Customer.class);
        assertNotNull(customer);
        assertNotNull(customer.getId());
        id = customer.getId();
    }

    @Test
    @Order(2)
    void findById() {
        Customer customer = restTemplate.getForObject("/customer/{id}", Customer.class, id);
        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals(id, customer.getId());
    }

    @Test
    @Order(2)
    void findAll() {
        Customer[] customers = restTemplate.getForObject("/customer", Customer[].class);
        assertTrue(customers.length > 0);
    }
}
