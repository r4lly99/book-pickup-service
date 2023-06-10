package com.online.demo.bookpickupservice.service;

import com.online.demo.bookpickupservice.client.OpenLibraryClient;
import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private OpenLibraryClient openLibraryClient;

    @Test
    void testGetBooksBySubject_Success(){
        String subject = "java";
        String title = "Java software solutions";

        SubjectsAPIResponse.Works works = SubjectsAPIResponse.Works.builder()
                .key("/works/OL94732W")
                .title(title)
                .authors(List.of(SubjectsAPIResponse.Works.Authors.builder()
                                .name("Lewis, John")
                                .build()))
                .availability(SubjectsAPIResponse.Works.Availability.builder()
                        .availableToBorrow(true)
                        .status("borrow_available")
                        .isbn("0201725975")
                        .build())
                .build();
        SubjectsAPIResponse mockApiResponse = SubjectsAPIResponse.builder()
                .name("java")
                .works(List.of(works))
                .build();

        Mockito.when(openLibraryClient.getBooksBySubject(subject)).thenReturn(mockApiResponse);
        List<BooksDTO> result = bookService.getBooksBySubject(subject);

        List<BooksDTO> expected = List.of(BooksDTO.builder()
                        .editionNumbers("0201725975")
                        .title(title)
                        .authors(List.of("Lewis, John"))
                        .status("borrow_available")
                        .availableToBorrow(true)
                .build());

        Assertions.assertEquals(expected, result);
    }

    @Test
    void testGetBooksBySubject_Failed(){
        String subject = "java";

        SubjectsAPIResponse mockApiResponse = SubjectsAPIResponse.builder()
                .name("java")
                .works(Collections.emptyList())
                .build();

        Mockito.when(openLibraryClient.getBooksBySubject(subject)).thenReturn(mockApiResponse);
        List<BooksDTO> result = bookService.getBooksBySubject(subject);

        Assertions.assertEquals(Collections.emptyList(), result);
    }


}
