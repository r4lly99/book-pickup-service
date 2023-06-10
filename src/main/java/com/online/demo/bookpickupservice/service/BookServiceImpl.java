package com.online.demo.bookpickupservice.service;

import com.online.demo.bookpickupservice.client.OpenLibraryClient;
import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.dto.SubjectsAPIResponse;
import com.online.demo.bookpickupservice.dto.SubmitBookRequest;
import com.online.demo.bookpickupservice.dto.SubmitBookResponse;
import com.online.demo.bookpickupservice.enumeration.SubmitResponseEnum;
import com.online.demo.bookpickupservice.model.BookDatabase;
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
        if (subjectsAPIResponse == null || subjectsAPIResponse.getWorks().isEmpty()) {
            return Collections.emptyList();
        }

        List<BooksDTO> booksDTOList = new ArrayList<>();
        BookDatabase bookDatabase = BookDatabase.INSTANCE;
        for (SubjectsAPIResponse.Works work : subjectsAPIResponse.getWorks()) {
            List<String> authorNames = extractAuthorNames(work.getAuthors());
            BooksDTO booksDTO = BooksDTO.builder()
                    .title(work.getTitle())
                    .authors(authorNames)
                    .build();
            if (work.getAvailability() != null) {
                booksDTO.setEditionNumbers(work.getEditionCount());
                booksDTO.setStatus(work.getAvailability().getStatus());
                booksDTO.setAvailableToBorrow(work.getAvailability().getAvailableToBorrow());
            }
            booksDTOList.add(booksDTO);
        }
        bookDatabase.update(booksDTOList);

        return booksDTOList;
    }

    @Override
    public SubmitBookResponse submitBookPickup(SubmitBookRequest submitBookRequest) {
        log.info("Get data from memory ");
        BookDatabase database = BookDatabase.INSTANCE;
        BooksDTO booksDTO = database.getByEditionNumber(submitBookRequest.getEditionNumbers());
        log.info("Result -> {}", booksDTO);
        if (booksDTO == null){
            log.error("Data not found or empty !");
            return buildFailedResponse();
        }
        if (Boolean.FALSE.equals(booksDTO.getAvailableToBorrow())){
            log.error("Unable to borrow the book!");
            return buildFailedResponse();
        }
       return buildSuccessResponse(booksDTO, submitBookRequest);
    }

    private List<String> extractAuthorNames(List<SubjectsAPIResponse.Works.Authors> authors) {
        if (authors == null) {
            return Collections.emptyList();
        }
        return authors.stream()
                .map(SubjectsAPIResponse.Works.Authors::getName)
                .collect(Collectors.toList());
    }

    private SubmitBookResponse buildSuccessResponse(BooksDTO booksDTO, SubmitBookRequest submitBookRequest){
        return SubmitBookResponse.builder()
                .status(SubmitResponseEnum.SUCCESS.toString())
                .book(booksDTO)
                .username(submitBookRequest.getUsername())
                .pickUpDateTime(submitBookRequest.getPickUpDateTime())
                .build();
    }

    private SubmitBookResponse buildFailedResponse(){
        return SubmitBookResponse.builder()
                .status(SubmitResponseEnum.FAILED.toString())
                .build();
    }
}
