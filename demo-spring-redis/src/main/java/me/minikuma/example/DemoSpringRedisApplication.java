package me.minikuma.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DemoSpringRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringRedisApplication.class, args);
    }
}
