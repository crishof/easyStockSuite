package org.crishof.stocksuitemono.exception.notFound;

import java.io.Serial;
import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(UUID id) {
        super("Category with id: " + id + " not found");
    }
}
