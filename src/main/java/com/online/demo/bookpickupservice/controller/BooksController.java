package com.online.demo.bookpickupservice.controller;

import com.online.demo.bookpickupservice.constant.BooksConstant;
import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.dto.SubmitBookRequest;
import com.online.demo.bookpickupservice.dto.SubmitBookResponse;
import com.online.demo.bookpickupservice.enumeration.SubmitResponseEnum;
import com.online.demo.bookpickupservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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

    @PostMapping(value = BooksConstant.API_BOOK_PICK_UP, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> submitBookPickUp(@Valid @RequestBody SubmitBookRequest submitBookRequest){
        try {
            log.info("Request " + BooksConstant.API_BOOK_PICK_UP + " with payload : " + submitBookRequest);
            SubmitBookResponse submitBookResponse = bookService.submitBookPickup(submitBookRequest);
            if (Objects.isNull(submitBookResponse)){
                return ResponseEntity.badRequest().build();
            }
            if (submitBookResponse.getStatus().contentEquals(SubmitResponseEnum.FAILED.name())){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(submitBookResponse);
        } catch (Exception e) {
            log.error(String.format("Error when submit book pickup: %s", submitBookRequest), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
