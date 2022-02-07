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
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

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

    @Test
    @DisplayName("list_operations 테스트 : 객체")
    void list_operations() {
        // given
        ProductDto p1 = new ProductDto();
        ProductDto p2 = new ProductDto();

        p1.setProductId(1000L);
        p1.setProductDescription("p1");
        p1.setProductName("p1-product");
        p1.setPrice(1000);
        p1.setQuantity(1);

        p2.setProductId(2000L);
        p2.setProductName("p2-product");
        p2.setProductDescription("p2");
        p2.setPrice(2000);
        p2.setQuantity(2);

        // when
        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(String.valueOf(p1.getProductId()), p1);
        listOperations.rightPush(String.valueOf(p2.getProductId()), p2);

        Object rightPop1 = listOperations.rightPop(String.valueOf(p2.getProductId()));
        LOGGER.info("rightPop1 = {}", rightPop1);
        Object rightPop2 = listOperations.rightPop(String.valueOf(p1.getProductId()));
        LOGGER.info("rightPop2 = {}", rightPop2);

        // then
        Assertions.assertThat(rightPop1).isEqualTo(p2);
        Assertions.assertThat(rightPop2).isEqualTo(p1);
    }
}
