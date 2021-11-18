package me.minikuma.external.config.rest;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    // rest template
    @Bean
    @Qualifier("restTemplateCustomizer")
    public RestTemplateCustomizer restTemplateCustomizer() {
        return new RestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"restTemplateCustomizer", "httpComponentsClientHttpRequestFactory"})
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .requestFactory(this::httpComponentsClientHttpRequestFactory)
                .setReadTimeout(Duration.ofSeconds(3))
                .setConnectTimeout(Duration.ofSeconds(3))
                .customizers(restTemplateCustomizer())
                .build();
    }

    @Bean
    @Qualifier("httpComponentsClientHttpRequestFactory")
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient());
        return factory;
    }

    // apache
    @Bean
    @Qualifier("poolingHttpClientConnectionManager")
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolManager = new PoolingHttpClientConnectionManager();
        poolManager.setMaxTotal(100); // open connection 수
        poolManager.setDefaultMaxPerRoute(5); // [IP + Port] 자원에 대해 수행할 커넥션 수
        return poolManager;
    }

    @Bean
    @Qualifier("httpClient")
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager())
                .build();
    }
}