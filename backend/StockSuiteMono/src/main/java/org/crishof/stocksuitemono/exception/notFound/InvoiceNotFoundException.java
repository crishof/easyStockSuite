package org.crishof.stocksuitemono.exception.notFound;

import java.io.Serial;
import java.util.UUID;

public class InvoiceNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public InvoiceNotFoundException() { super();}
    public InvoiceNotFoundException(String message) { super(message);};

    public InvoiceNotFoundException(UUID uuid){

        super("Invoice with id " + uuid + " not found");
    }
}