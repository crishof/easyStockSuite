package com.crishof.transactionsv.service;

import com.crishof.transactionsv.dto.TransactionRequest;
import com.crishof.transactionsv.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest transactionRequest);

    TransactionResponse editTransaction(UUID transactionId, TransactionRequest transactionRequest);

    String deleteTransaction(UUID transactionId);

    List<TransactionResponse> getAllTransactions(UUID supplierId);
}
