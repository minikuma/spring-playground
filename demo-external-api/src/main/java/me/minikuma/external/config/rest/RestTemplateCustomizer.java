package me.minikuma.external.config.rest;

import org.springframework.web.client.RestTemplate;

public class RestTemplateCustomizer implements org.springframework.boot.web.client.RestTemplateCustomizer {

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.getInterceptors().add(new RestTemplateInterceptor());
    }
}
