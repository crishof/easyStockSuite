package org.crishof.stocksv.exception;

import java.io.Serial;
import java.util.UUID;

public class StockNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StockNotFoundException() {
        super();
    }

    public StockNotFoundException(String message) {
        super(message);
    }

    public StockNotFoundException(UUID stockId) {
        super("Stock with id: " + stockId + " not found");
    }
}
