package com.crishof.transactionsv.service;

import com.crishof.transactionsv.apiclient.PaymentAPIClient;
import com.crishof.transactionsv.apiclient.SupplierInvoiceAPIClient;
import com.crishof.transactionsv.dto.TransactionRequest;
import com.crishof.transactionsv.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final SupplierInvoiceAPIClient supplierInvoiceAPIClient;
    private final PaymentAPIClient paymentAPIClient;

    @Override
    public List<TransactionResponse> getAllTransactions(UUID supplierId) {

        ResponseEntity<List<TransactionRequest>> invoiceResponses = supplierInvoiceAPIClient.getAllTransactionsBySupplier(supplierId);
        ResponseEntity<List<TransactionRequest>> paymentResponses = paymentAPIClient.getAllBySupplier(supplierId);

        List<TransactionResponse> paymentTransactions = Objects.requireNonNull(paymentResponses.getBody()).stream()
                .map(payment -> toTransactionResponse(payment, "recipe"))  // Asigna "recipe" a las transacciones de pagos
                .toList();

        List<TransactionResponse> invoiceTransactions = Objects.requireNonNull(invoiceResponses.getBody()).stream()
                .map(invoice -> toTransactionResponse(invoice, "invoice"))  // Asigna "invoice" a las transacciones de facturas
                .toList();

        List<TransactionResponse> allTransactions = new ArrayList<>(invoiceTransactions);
        allTransactions.addAll(paymentTransactions);

        return allTransactions.stream()
                .sorted(Comparator.comparing(TransactionResponse::getDate))
                .toList();
    }

    private TransactionResponse toTransactionResponse(TransactionRequest transactionRequest, String type) {
        return TransactionResponse.builder()
                .transactionId(transactionRequest.getTransactionId())
                .type(type)
                .date(transactionRequest.getDate())
                .transactionNumber(transactionRequest.getTransactionNumber())
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .build();
    }
}
