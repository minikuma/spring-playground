package me.minikuma.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

    private final RedisProperties  redisProperties;
    private RedisServer redisServer;

    @PostConstruct
    public void start() throws IOException {
        redisServer = new RedisServer(redisProperties.getPort());
        redisServer.start();
    }

    @PreDestroy
    public void stop() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
