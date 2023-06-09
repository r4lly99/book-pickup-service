package com.online.demo.bookpickupservice.controller;

import com.online.demo.bookpickupservice.client.OpenLibraryClient;
import com.online.demo.bookpickupservice.constant.BooksConstant;
import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books/")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final OpenLibraryClient openLibClient;

    @GetMapping(value = BooksConstant.BOOK_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectsAPIResponse> getListofBooks(@RequestParam String subject){
        return ResponseEntity.ok(openLibClient.getBooksFromOpenLibBySubject(subject));
    }

}
