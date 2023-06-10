package com.online.demo.bookpickupservice.model;

import com.online.demo.bookpickupservice.dto.BooksDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BookDatabase {

    INSTANCE;

    private final Map<Integer, BooksDTO> database;

    BookDatabase() {
        database = new HashMap<>();
    }

    public void reset() {
        database.clear();
    }

    public void update(List<BooksDTO> response) {
        if (response == null) {
            return;
        }

        for (BooksDTO booksDTO : response) {
            if (booksDTO.getEditionNumbers() != null) {
                database.put(booksDTO.getEditionNumbers(), booksDTO);
            }
        }
    }

    public BooksDTO getByEditionNumber(Integer editionNumber) {
        return database.get(editionNumber);
    }

}
