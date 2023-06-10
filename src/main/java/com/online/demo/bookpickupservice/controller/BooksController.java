package com.online.demo.bookpickupservice.controller;

import com.online.demo.bookpickupservice.constant.BooksConstant;
import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books/")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BookService bookService;

    @GetMapping(value = BooksConstant.API_BOOK_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListofBooks(@RequestParam String subject){
        log.info("Request " + BooksConstant.API_BOOK_LIST + " with subject : " + subject);
        List<BooksDTO> booksDTOList = bookService.getBooksBySubject(subject);
        if (booksDTOList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookService.getBooksBySubject(subject));
    }

}
