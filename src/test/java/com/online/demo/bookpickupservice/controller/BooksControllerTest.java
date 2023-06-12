package com.online.demo.bookpickupservice.controller;

import com.online.demo.bookpickupservice.constant.BooksConstant;
import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.dto.SubmitBookRequest;
import com.online.demo.bookpickupservice.dto.SubmitBookResponse;
import com.online.demo.bookpickupservice.enumeration.SubmitResponseEnum;
import com.online.demo.bookpickupservice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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
    void testGetListOfBooks_success(){
        String subject = "java";
        List<BooksDTO> mockResult = List.of(BooksDTO.builder()
                .title("Java software solutions")
                .editionNumbers(26)
                .authors(List.of("Lewis, John"))
                .status("borrow_available")
                .availableToBorrow(true)
                .build());
        Mockito.when(bookService.getBooksBySubject(subject)).thenReturn(mockResult);

        ResponseEntity<?> result = booksController.getListofBooks(subject);
        List<BooksDTO> expectedResult = List.of(BooksDTO.builder()
                        .title("Java software solutions")
                        .editionNumbers(26)
                        .authors(List.of("Lewis, John"))
                        .status("borrow_available")
                        .availableToBorrow(true)
                .build());

        assertEquals(expectedResult, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetListOfBooks_Failed(){
        String subject = "java";
        Mockito.when(bookService.getBooksBySubject(subject)).thenReturn(Collections.emptyList());

        ResponseEntity<?> result = booksController.getListofBooks(subject);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testSubmitBookPickUp_Success(){
        SubmitBookRequest submitBookRequest = SubmitBookRequest.builder()
                .editionNumbers(26)
                .pickUpDateTime(LocalDateTime.of(2023, 6, 10, 10, 0, 0))
                .phoneNumber("081122334456")
                .username("Joni")
                .build();
        BooksDTO mockBookDTO = BooksDTO.builder()
                .availableToBorrow(true)
                .authors(List.of("Ramsey"))
                .title("Head First Design Pattern")
                .build();

        Mockito.when(bookService.submitBookPickup(submitBookRequest)).thenReturn(SubmitBookResponse.builder()
                        .status(SubmitResponseEnum.SUCCESS.toString())
                        .book(mockBookDTO)
                        .username("Joni")
                        .pickUpDateTime(LocalDateTime.of(2023, 6, 10, 10, 0, 0))
                .build());

        SubmitBookResponse expectedResponse = SubmitBookResponse.builder()
                .status(SubmitResponseEnum.SUCCESS.toString())
                .book(mockBookDTO)
                .username("Joni")
                .pickUpDateTime(LocalDateTime.of(2023, 6, 10, 10, 0, 0))
                .build();

        ResponseEntity<?> result = booksController.submitBookPickUp(submitBookRequest);

        Assertions.assertEquals(expectedResponse, result.getBody());
    }

    @Test
    void testSubmitBookPickUp_Failed1(){
        SubmitBookRequest submitBookRequest = SubmitBookRequest.builder()
                .editionNumbers(26)
                .pickUpDateTime(LocalDateTime.of(2023, 6, 10, 10, 0, 0))
                .phoneNumber("081122334456")
                .username("Joni")
                .build();
        Mockito.when(bookService.submitBookPickup(submitBookRequest)).thenReturn(SubmitBookResponse.builder()
                        .status(SubmitResponseEnum.FAILED.name())
                        .build());
        ResponseEntity<?> result = booksController.submitBookPickUp(submitBookRequest);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testSubmitBookPickUp_Failed2(){
        SubmitBookRequest submitBookRequest = SubmitBookRequest.builder()
                .editionNumbers(26)
                .pickUpDateTime(LocalDateTime.now().minusDays(1))
                .phoneNumber("081122334456")
                .username("Joni")
                .build();
        Mockito.when(bookService.submitBookPickup(submitBookRequest)).thenReturn(SubmitBookResponse.builder()
                        .status(SubmitResponseEnum.ERROR.name())
                        .errorMessage(BooksConstant.ERROR_PICK_UP_DATE_TIME)
                .build());
        ResponseEntity<?> result = booksController.submitBookPickUp(submitBookRequest);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
