package org.crishof.dimensionsv.exception;

import java.util.UUID;

public class DimensionNotFoundException extends RuntimeException {
    public DimensionNotFoundException(String message) {
        super(message);
    }

    public DimensionNotFoundException() {
        super();
    }

    public DimensionNotFoundException(UUID id) {
        super(String.format("Dimension not found with id %s", id));
    }
}
