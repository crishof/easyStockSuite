package org.crishof.stocksuitemono.exception.notFound;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id) {
        super("Product with id: " + id + "not found");
    }
}
