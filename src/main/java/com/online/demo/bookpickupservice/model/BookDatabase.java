package com.online.demo.bookpickupservice.model;

import com.online.demo.bookpickupservice.dto.BooksDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDatabase {

    private static BookDatabase instance;
    private final Map<Integer, BooksDTO> database;

    private BookDatabase() {
        database = new HashMap<>();
    }

    public static synchronized BookDatabase getInstance() {
        if (instance == null) {
            instance = new BookDatabase();
        }
        return instance;
    }

    public void reset() {
        database.clear();
    }

    public void update(List<BooksDTO> response) {
        if (response == null ) {
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
