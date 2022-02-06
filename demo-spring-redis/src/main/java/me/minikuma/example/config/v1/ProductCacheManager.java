package me.minikuma.example.config.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

import static org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;

@Profile("dev")
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class ProductCacheManager extends CachingConfigurerSupport {

    private final LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManagerBuilder builder = RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair
                                .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofSeconds(10));
        builder.cacheDefaults(configuration);
        return builder.build();
    }
}
