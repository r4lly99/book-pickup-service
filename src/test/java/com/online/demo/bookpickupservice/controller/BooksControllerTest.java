package com.online.demo.bookpickupservice.controller;

import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BooksControllerTest {

    @InjectMocks
    private BooksController booksController;

    @Mock
    private BookService bookService;

    @Test
    void successGetListOfBooks(){
        String subject = "java";
        List<BooksDTO> mockResult = List.of(BooksDTO.builder()
                .title("Java software solutions")
                .editionNumbers("0201725975")
                .authors(List.of("Lewis, John"))
                .status("borrow_available")
                .availableToBorrow(true)
                .build());
        Mockito.when(bookService.getBooksBySubject(Mockito.eq(subject))).thenReturn(mockResult);

        ResponseEntity<?> result = booksController.getListofBooks(subject);
        List<BooksDTO> expectedResult = List.of(BooksDTO.builder()
                        .title("Java software solutions")
                        .editionNumbers("0201725975")
                        .authors(List.of("Lewis, John"))
                        .status("borrow_available")
                        .availableToBorrow(true)
                .build());

        assertEquals(expectedResult, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void failedGetListOfBooks(){
        String subject = "java";
        Mockito.when(bookService.getBooksBySubject(Mockito.eq(subject))).thenReturn(Collections.emptyList());

        ResponseEntity<?> result = booksController.getListofBooks(subject);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
