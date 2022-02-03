package me.minikuma.example.operation;

import me.minikuma.example.common.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Operational Values Test
 */

@SpringBootTest
public class OperationalValuesTest {

    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    @DisplayName("opsForValue() -> ValueOperations : String 작업")
    void value_operations() {
        // given
        String key = "test-key:1";
        String value = "test-value:1";
        // when
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        LOGGER.info("valueOperations = {}", valueOperations.get(key));
        // then
        Assertions.assertThat(value).isEqualTo(valueOperations.get(key));
    }

    @Test
    @DisplayName("opsForValue() -> ValueOperations : Object 작업")
    void object_operations() {
        // given
        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductName("name");
        productDto.setProductDescription("desc");
        productDto.setPrice(111);
        productDto.setQuantity(1);

        // when
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(productDto.getProductId(), productDto);
        LOGGER.info("valueOperations = {}", opsForValue.get(productDto.getProductId()));
        // then
        Assertions.assertThat(productDto).isEqualTo(opsForValue.get(productDto.getProductId()));
    }


    @Test
    @DisplayName("opsForHash() -> HashOperations : 해시 작업")
    void hash_operations() {
        // given
        String key = "test-hash-key:2";
        String value = "test-hash-value:2";
        // when
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(key, key, value);

        LOGGER.info("valueOperations = {}", hashOperations.get(key, key));
        // then
        Assertions.assertThat(value).isEqualTo(hashOperations.get(key, key));
    }
}
