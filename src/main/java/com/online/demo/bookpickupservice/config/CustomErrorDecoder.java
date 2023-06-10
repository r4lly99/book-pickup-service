package com.online.demo.bookpickupservice.config;

import com.online.demo.bookpickupservice.exception.NotFoundException;
import com.online.demo.bookpickupservice.exception.ResponseException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        String requestUrl = response.request().url();
        String responseEntity = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is5xxServerError()) {
            log.error("Server error | HTTP Status Code: {} With Body {}", responseStatus, responseEntity);
        } else if (responseStatus.equals(HttpStatus.UNAUTHORIZED) || responseStatus.equals(HttpStatus.BAD_REQUEST)) {
            log.error("Access or Invalid Format error | HTTP Status Code: {} With Body {}", responseStatus, responseEntity);
        } else if (responseStatus.equals(HttpStatus.FORBIDDEN)) {
            log.error("Client Reach Max Hits | HTTP Status Code: {} With Body {}", responseStatus, responseEntity);
        } else if (responseStatus.equals(HttpStatus.NOT_FOUND)) {
            log.error("Data Not Found | HTTP Status Code: {} With Body {}", responseStatus, responseEntity);
            return new NotFoundException("Resource not found");
        }
        return new ResponseException(requestUrl, new Throwable(responseEntity));
    }
}
