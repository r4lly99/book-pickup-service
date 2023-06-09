package com.online.demo.bookpickupservice.service;

import com.online.demo.bookpickupservice.dto.BooksDTO;

import java.util.List;

public interface BookService {

    List<BooksDTO> getBooksBySubject(String subject);

}
