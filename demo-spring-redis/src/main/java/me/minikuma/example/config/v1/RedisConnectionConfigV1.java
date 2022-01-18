package me.minikuma.example.config.v1;

import lombok.RequiredArgsConstructor;
import me.minikuma.example.config.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Lettuce Connector
 */

@Configuration
@EnableRedisRepositories(basePackages = "me.minikuma.example.repository.cache")
@RequiredArgsConstructor
public class RedisConnectionConfigV1 {

    private final RedisProperties redisProperties;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setPassword(redisProperties.getPassword());
        config.setPort(redisProperties.getPort());
        config.setHostName(redisProperties.getHost());
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setDefaultSerializer(RedisSerializer.string());
        return redisTemplate;
    }
}
