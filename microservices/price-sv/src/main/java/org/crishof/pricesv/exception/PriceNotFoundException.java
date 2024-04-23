package org.crishof.pricesv.exception;

import java.io.Serial;
import java.util.UUID;

public class PriceNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PriceNotFoundException() {
        super();
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(UUID uuid) {
        super("Prices with id " + uuid + " not found");
    }
}
