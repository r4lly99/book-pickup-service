package com.online.demo.bookpickupservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooksConstant {

    public static final String API_BOOK_LIST = "book-list";
    public static final String API_BOOK_PICK_UP = "pickup";
    public static final String ERROR_PICK_UP_DATE_TIME = "The pick-up schedule must be in the next day";

}
