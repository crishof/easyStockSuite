package org.crishof.stocksuitemono.exception.notFound;

import java.io.Serial;

public class BrandNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BrandNotFoundException() {
        super();
    }

    public BrandNotFoundException(String message) {
        super(message);
    }

    public BrandNotFoundException(Long id) {
        super("Brand with id: " + id + "not found");
    }
}
