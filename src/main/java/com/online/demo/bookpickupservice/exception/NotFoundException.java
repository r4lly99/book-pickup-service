package com.online.demo.bookpickupservice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String s) {
        super(s);
    }

}
