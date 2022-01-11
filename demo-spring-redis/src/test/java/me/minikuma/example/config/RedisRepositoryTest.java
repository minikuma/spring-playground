package me.minikuma.example.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
public class RedisRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(RedisRepositoryTest.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    @DisplayName("lettuce connector 를 사용하는 케이스 opsForValue() 메서드")
    void lettuce_connector_redis_template_opsForValue() {

        String key = "sample";
        String value = "test";

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value);

        String findValue = opsForValue.get(key);

        logger.info("find value = {}", findValue);

        Assertions.assertThat(findValue).isEqualTo(value);
    }

    @Test
    @DisplayName("lettuce connector 를 사용하는 케이스 opsForList() 메서드")
    void lettuce_connector_redis_template_opsForList() {
        String key = "key";
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, "S");
        listOperations.rightPush(key, "A");
        listOperations.rightPush(key, "M");
        listOperations.rightPush(key, "P");
        listOperations.rightPush(key, "L");
        listOperations.rightPush(key, "E");
        // S A M P L E
        Long beforeSize = listOperations.size(key);
        listOperations.leftPop(key); // S

        Long afterSize = listOperations.size(key);
        List<String> findValues = listOperations.range(key, 0, afterSize);

        logger.info("find Values = {}", findValues.toString());

        Assertions.assertThat(beforeSize).isEqualTo(6);
        Assertions.assertThat(findValues.get(0)).isEqualTo("A");
    }
}
