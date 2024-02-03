package org.crishof.stocksuitemono.exception.notFound;

import java.io.Serial;

public class SupplierNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SupplierNotFoundException() {
        super();
    }

    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException(Long id) {
        super("Supplier with id: " + id + " not found");
    }
}
