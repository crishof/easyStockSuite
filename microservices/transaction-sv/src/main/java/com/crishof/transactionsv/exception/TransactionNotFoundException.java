package com.crishof.transactionsv.exception;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
        super(message);
    }

    public TransactionNotFoundException(UUID transactionId) {
        super(String.format("Transaction with id %s not found", transactionId));
    }
}
