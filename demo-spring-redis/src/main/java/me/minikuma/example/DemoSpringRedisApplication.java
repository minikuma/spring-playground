package me.minikuma.example;

import lombok.extern.slf4j.Slf4j;
import me.minikuma.example.domain.User;
import me.minikuma.example.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DemoSpringRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringRedisApplication.class, args);
    }

    @Slf4j
    @Component
    static class DefaultDataPopulate implements ApplicationRunner {
        @Autowired
        UserRepository userRepository;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            User user = User.builder()
                    .id("ABCD")
                    .age(1000)
                    .address("굿")
                    .build();

            userRepository.save(user);
            log.info("save user = {}", user);
            userRepository.findById(user.getId());
            log.info("user count = {}", userRepository.count());
//            userRepository.delete(user);
            log.info("user = {}", userRepository.findAll());
        }
    }
}
