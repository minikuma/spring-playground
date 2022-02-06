package me.minikuma.example.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class TestRedisConfig {
    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RedisServer redisServer;

    public TestRedisConfig(@Value("${spring.redis.port}") int redisPort) {
        this.redisServer = new RedisServer(redisPort);
        LOGGER.info("Create Embedded Redis Server");
    }

    @PostConstruct
    public void startRedis() {
        redisServer.start();
        LOGGER.info("Embedded Redis Server Start");
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
        LOGGER.info("Embedded Redis Server Stop");
    }
 }
