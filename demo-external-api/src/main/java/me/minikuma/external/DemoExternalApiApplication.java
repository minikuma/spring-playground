package me.minikuma.external;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class DemoExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoExternalApiApplication.class, args);
    }

}
