package com.online.demo.bookpickupservice.service;

import com.online.demo.bookpickupservice.client.OpenLibraryClient;
import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService{

    private final OpenLibraryClient openLibraryClient;

    @Override
    @Cacheable(value = "books-list", key = "#subject")
    public List<BooksDTO> getBooksBySubject(String subject) {

        SubjectsAPIResponse subjectsAPIResponse = openLibraryClient.getBooksBySubject(subject);
        if (subjectsAPIResponse == null || subjectsAPIResponse.getWorks().size() == 0) {
            return Collections.emptyList();
        }

        List<BooksDTO> booksDTOList = new ArrayList<>();
        for (SubjectsAPIResponse.Works work : subjectsAPIResponse.getWorks()) {
            List<String> authorNames = extractAuthorNames(work.getAuthors());
            BooksDTO booksDTO = BooksDTO.builder()
                    .title(work.getTitle())
                    .authors(authorNames)
                    .build();
            if (work.getAvailability() != null) {
                booksDTO.setEditionNumbers(work.getAvailability().getIsbn());
                booksDTO.setStatus(work.getAvailability().getStatus());
                booksDTO.setAvailableToBorrow(work.getAvailability().getAvailableToBorrow());
            }
            booksDTOList.add(booksDTO);
        }

        return booksDTOList;
    }

    private List<String> extractAuthorNames(List<SubjectsAPIResponse.Works.Authors> authors) {
        if (authors == null) {
            return Collections.emptyList();
        }
        return authors.stream()
                .map(SubjectsAPIResponse.Works.Authors::getName)
                .collect(Collectors.toList());
    }
}
