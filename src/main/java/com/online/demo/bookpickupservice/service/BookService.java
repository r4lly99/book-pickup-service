package com.online.demo.bookpickupservice.service;

import com.online.demo.bookpickupservice.dto.BooksDTO;
import com.online.demo.bookpickupservice.dto.SubmitBookRequest;
import com.online.demo.bookpickupservice.dto.SubmitBookResponse;

import java.util.List;

public interface BookService {

    List<BooksDTO> getBooksBySubject(String subject);

    SubmitBookResponse submitBookPickup(SubmitBookRequest submitBookRequest);

}
