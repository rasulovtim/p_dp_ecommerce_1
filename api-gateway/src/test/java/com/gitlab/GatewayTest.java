package com.gitlab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GatewayTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void should_reroute_to_correct_port() {
        assertTrue(makeRequest("/api/example").contains(":8080"));
        assertTrue(makeRequest("/api/bank-card").contains(":8080"));
        assertTrue(makeRequest("/api/passport").contains(":8080"));
        assertTrue(makeRequest("/api/personal_address").contains(":8080"));
        assertTrue(makeRequest("/api/pickup_point").contains(":8080"));
        assertTrue(makeRequest("/api/postomat").contains(":8080"));
        assertTrue(makeRequest("/api/images").contains(":8080"));
        assertTrue(makeRequest("/api/product").contains(":8080"));

        assertFalse(makeRequest("/api/do/not/exist").contains(":8080"));
    }

    private String makeRequest(String path) {
        WebTestClient client = WebTestClient.bindToApplicationContext(this.context).build();
        return client.get().uri(path).exchange().returnResult(String.class).toString();
    }
}