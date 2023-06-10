package com.online.demo.bookpickupservice.client;

import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import com.online.demo.bookpickupservice.exception.NotFoundException;
import com.online.demo.bookpickupservice.exception.ResponseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenLibraryClientTest {

    @Mock
    private OpenLibraryClient openLibraryClient;

    @Test
    void testGetBooksBySubject_Success() {
        SubjectsAPIResponse expectedResponse = new SubjectsAPIResponse();
        expectedResponse.setName("java");
        when(openLibraryClient.getBooksBySubject("java")).thenReturn(expectedResponse);
        SubjectsAPIResponse response = openLibraryClient.getBooksBySubject("java");
        assertEquals(expectedResponse, response);
        verify(openLibraryClient).getBooksBySubject("java");
    }

    @Test
    void testGetBooksBySubject_NotFound() {
        when(openLibraryClient.getBooksBySubject("java")).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> openLibraryClient.getBooksBySubject("java"));
        verify(openLibraryClient).getBooksBySubject("java");
    }

    @Test
    void testGetBooksBySubject_ServerError() {
        when(openLibraryClient.getBooksBySubject("java")).thenThrow(ResponseException.class);
        assertThrows(ResponseException.class, () -> openLibraryClient.getBooksBySubject("java"));
        verify(openLibraryClient).getBooksBySubject("java");
    }

    @Test
    void testGetBooksBySubject_Forbidden() {
        when(openLibraryClient.getBooksBySubject("java")).thenThrow(ResponseException.class);
        assertThrows(ResponseException.class, () -> openLibraryClient.getBooksBySubject("java"));
        verify(openLibraryClient).getBooksBySubject("java");
    }
}

