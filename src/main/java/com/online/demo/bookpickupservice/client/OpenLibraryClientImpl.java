package com.online.demo.bookpickupservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.demo.bookpickupservice.constant.BooksConstant;
import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenLibraryClientImpl implements OpenLibraryClient{

    private final RestTemplate restTemplate;

    @Value("${open.library.url}")
    private String openLibraryUrl;

    @Override
    @SneakyThrows
    public SubjectsAPIResponse getBooksFromOpenLibBySubject(String subject) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(openLibraryUrl+ "/"+ subject + BooksConstant.OPEN_LIB_SUFFIX,
                        HttpMethod.GET,
                        request,
                        String.class);
        final ObjectMapper mapper = new ObjectMapper();
        if (response.getStatusCode().is2xxSuccessful()){
            log.info("Response SubjectsAPIResponse [{}]",response.getBody());
            return mapper.readValue(response.getBody(), SubjectsAPIResponse.class);
        }
        return null;
    }
}
