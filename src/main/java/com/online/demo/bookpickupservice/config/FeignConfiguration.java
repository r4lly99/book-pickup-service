package com.online.demo.bookpickupservice.config;

import com.online.demo.bookpickupservice.client.OpenLibraryClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public OpenLibraryClient openLibraryClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(OpenLibraryClient.class, "http://openlibrary.org");
    }
}
