package me.minikuma.example.operation;

import me.minikuma.example.common.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.persistence.EntityManagerFactory;

/**
 * Operational Values Test
 */

@SpringBootTest
public class OperationalValuesTest {

    final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Test
    @DisplayName("opsForValue() -> ValueOperations : 문자열 작업")
    void value_operations() {
        // given
        String key = "test-key:1";
        String value = "test-value:1";

        // when
        ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value);
        LOGGER.info("valueOperations = {}", opsForValue.get(key));

        // then
        Assertions.assertThat(value).isEqualTo(opsForValue.get(key));
    }

    @Test
    @DisplayName("opsForValue() -> ValueOperations : 객체 작업")
    void object_operations() {
        // given
        ProductDto productDto = new ProductDto();
        productDto.setProductId(1L);
        productDto.setProductName("name");
        productDto.setProductDescription("desc");
        productDto.setPrice(111);
        productDto.setQuantity(1);

        // when
        ValueOperations<Object, Object> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(String.valueOf(productDto.getProductId()), productDto);

        String productKey = String.valueOf(productDto.getProductId());

        LOGGER.info("valueOperations By Object = {}", opsForValue.get(productKey));

        // then
        Assertions.assertThat(productDto).isEqualTo(opsForValue.get(productKey));
    }


    @Test
    @DisplayName("opsForHash() -> HashOperations : 해시 작업 (문자열)")
    void hash_operations() {
        // given
        String key = "test-hash-key:2";
        String value = "test-hash-value:2";
        String hashKey = "HASH_KEY";
        // when
        HashOperations<Object, Object, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(key, hashKey, value);

        LOGGER.info("valueOperations = {}", opsForHash.get(key, hashKey));
        // then
        Assertions.assertThat(value).isEqualTo(opsForHash.get(key, hashKey));
    }
}
