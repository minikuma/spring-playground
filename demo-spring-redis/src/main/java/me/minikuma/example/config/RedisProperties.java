package me.minikuma.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter @Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private String host;
    private Integer port;
    private String password;
}
