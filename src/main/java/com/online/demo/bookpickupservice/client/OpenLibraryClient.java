package com.online.demo.bookpickupservice.client;

import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;

public interface OpenLibraryClient {

    SubjectsAPIResponse getBooksFromOpenLibBySubject(String subject);

}
