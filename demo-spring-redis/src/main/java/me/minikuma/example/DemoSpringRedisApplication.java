package me.minikuma.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableJpaRepositories(basePackages= "me.minikuma.example.repository.jpa")
public class DemoSpringRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringRedisApplication.class, args);
    }
}
