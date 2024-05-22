package com.crishof.supplierinvoicesv.exception;

import java.util.UUID;

public class InvoiceNotFoundException extends RuntimeException {

    public InvoiceNotFoundException(String message) {
        super(message);
    }

    public InvoiceNotFoundException(UUID invoiceId) {
        super("Invoice with id " + invoiceId + " not found");
    }
}
