package org.crishof.stocksuitemono.exception.duplicated;

public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super();
    }

    public DuplicateNameException(String message) {
        super(message);
    }
}
