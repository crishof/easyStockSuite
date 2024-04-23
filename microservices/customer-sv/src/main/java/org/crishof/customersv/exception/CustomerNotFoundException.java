package org.crishof.customersv.exception;

import java.io.Serial;
import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(UUID id) {
        super("Customer with id: " + id + " not found");
    }
}
