package org.crishof.addresssv.exception;

import java.util.UUID;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException() {
        super();
    }

    public AddressNotFoundException(String message) {
        super(message);
    }

    public AddressNotFoundException(UUID id) {
        super(String.format("Address with id %s not found", id));
    }
}
