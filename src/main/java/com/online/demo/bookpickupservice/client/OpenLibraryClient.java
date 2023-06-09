package com.online.demo.bookpickupservice.client;

import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import feign.Param;
import feign.RequestLine;

public interface OpenLibraryClient {

    @RequestLine("GET /subjects/{subject}.json")
    SubjectsAPIResponse getBooksBySubject(@Param("subject") String subject);

}
