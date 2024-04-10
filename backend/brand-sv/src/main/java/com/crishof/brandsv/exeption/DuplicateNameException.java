package com.crishof.brandsv.exeption;

public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super();
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
