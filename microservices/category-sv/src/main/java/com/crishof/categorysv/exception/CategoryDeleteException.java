package com.crishof.categorysv.exception;

import java.io.Serial;

public class CategoryDeleteException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CategoryDeleteException() {
        super();
    }

    public CategoryDeleteException(String message) {
        super(message);
    }

}
