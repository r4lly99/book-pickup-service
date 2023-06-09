package com.online.demo.bookpickupservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final RestTemplateBuilder restTemplateBuilder;
    static final int TIMEOUT = 5000;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder
                .errorHandler(new CustomClientErrorHandler())
                .setConnectTimeout(Duration.ofMillis(TIMEOUT))
                .setReadTimeout(Duration.ofMillis(TIMEOUT))
                .build();
    }

}
