package com.crishof.brandsv.exception;

import java.io.Serial;

public class ProductsAssociatedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductsAssociatedException() {
        super();
    }

    public ProductsAssociatedException(String message) {
        super(message);
    }

}
