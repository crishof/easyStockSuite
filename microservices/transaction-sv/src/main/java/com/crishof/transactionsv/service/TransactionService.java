package com.crishof.transactionsv.service;

import com.crishof.transactionsv.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<TransactionResponse> getAllTransactions(UUID supplierId);
}
