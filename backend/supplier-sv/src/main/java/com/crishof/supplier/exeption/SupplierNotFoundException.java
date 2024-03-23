package com.crishof.supplier.exeption;

import java.io.Serial;
import java.util.UUID;

public class SupplierNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SupplierNotFoundException() {
        super();
    }

    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException(UUID id) {
        super("Supplier with id: " + id + " not found");
    }
}
