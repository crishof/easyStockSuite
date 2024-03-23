package com.crishof.brandsv.exeption;

import java.io.Serial;
import java.util.UUID;

public class BrandNotFoundExeption extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BrandNotFoundExeption() {
        super();
    }

    public BrandNotFoundExeption(String message) {
        super(message);
    }

    public BrandNotFoundExeption(UUID id) {
        super("Brand with id: " + id + "not found");
    }
}
