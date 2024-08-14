package com.example.__Circuit_Breaker;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DbController {

    @GetMapping("/redis")
    @CircuitBreaker(name = "backendService", fallbackMethod = "callToDatabase")
    public String callToRedisDb() {
        RestTemplate rt=new RestTemplate();
        ResponseEntity<String> forEntity = rt.getForEntity("https://jsonplaceholder.typicode.com/users", String.class);
        return forEntity.getBody();
    }

    public String callToDatabase(Throwable throwable) {
        // Fallback method to call the database when Redis call fails
        return "db is called ";
    }
}
