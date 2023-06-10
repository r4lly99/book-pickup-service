package com.online.demo.bookpickupservice.exception;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {

    public ResponseException(String s) {
        super(s);
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}