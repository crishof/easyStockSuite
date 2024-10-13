package com.crishof.brandsv.exception;

import java.io.Serial;
import java.util.UUID;

public class BrandNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BrandNotFoundException() {
        super();
    }

    public BrandNotFoundException(String message) {
        super(message);
    }

    public BrandNotFoundException(UUID id) {
        super("Brand with id: " + id + " not found");
    }

}
