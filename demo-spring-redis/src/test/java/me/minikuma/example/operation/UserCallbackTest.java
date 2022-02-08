package me.minikuma.example.operation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class UserCallbackTest {

    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StringRedisTemplate redisTemplate;


    @Test
    @DisplayName("Call Back Test")
    void use_call_back_test() {
        Object object = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = connection.dbSize();
                ((StringRedisConnection) connection).set("key", "value");
                return ((StringRedisConnection) connection).get("key");
            }
        });

        LOGGER.info("object = {}", object);
        String expectedValue = "value";
        Assertions.assertThat(object).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Call Back 을 활용한 한번에 다건 레디스 등록 예제")
    void user_call_back_한번에_다건_등록() {
        // given
        redisTemplate.opsForValue().set("key1", "10");

        // when
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                for (int i = 0; i < 4; i++) {
                    redisTemplate.opsForValue().increment("key1", 1);
                }

                return null;
            }
        });

        String totalCount = redisTemplate.opsForValue().get("key1");
        LOGGER.info("get total Count = {}", totalCount);
        // then
        Assertions.assertThat(totalCount).isEqualTo("14");
    }

    @Test
    @DisplayName("레디스 트랜젝션 처리 테스트")
    void use_transactional() {
        // given
        redisTemplate.opsForValue().set("key1", "10");
        redisTemplate.opsForSet().add("key2", "key1-value");
        Assertions.assertThat(redisTemplate.opsForSet().isMember("key2", "30")).isFalse();

        // when
        redisTemplate.execute(new SessionCallback<Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.watch((K) "key1"); // myKey1 값 변경 체크
                operations.multi(); // 트랜젝션 시작

                operations.opsForValue().increment((K) "key1", 1); // 작업 1
                operations.opsForSet().add((K) "key2", (V) "key2-value2"); // 작업 2

                return operations.exec(); // 트랜젝션 커밋
            }
        });
        // then
        LOGGER.info("myKey1 data : {}", redisTemplate.opsForValue().get("key1"));
        LOGGER.info("myKey2 data : {}", redisTemplate.opsForSet().members("key2"));

        Assertions.assertThat(redisTemplate.opsForValue().get("key1")).isEqualTo("11");
        Assertions.assertThat(redisTemplate.opsForSet().isMember("key2", "key2-value2")).isTrue();
    }
}
