package com.crishof.transactionsv.service;

import com.crishof.transactionsv.apiClient.SupplierInvoiceAPIClient;
import com.crishof.transactionsv.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final SupplierInvoiceAPIClient supplierInvoiceAPIClient;

    @Override
    public List<TransactionResponse> getAllTransactions(UUID supplierId) {

        ResponseEntity<List<TransactionResponse>> invoiceResponses = supplierInvoiceAPIClient.getAllTransactionsBySupplier(supplierId);

        return invoiceResponses.getBody();

    }
}
