package com.crishof.transactionsv.service;

import com.crishof.transactionsv.apiclient.PaymentAPIClient;
import com.crishof.transactionsv.apiclient.SupplierInvoiceAPIClient;
import com.crishof.transactionsv.dto.TransactionRequest;
import com.crishof.transactionsv.dto.TransactionResponse;
import com.crishof.transactionsv.exception.TransactionNotFoundException;
import com.crishof.transactionsv.model.Transaction;
import com.crishof.transactionsv.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final SupplierInvoiceAPIClient supplierInvoiceAPIClient;
    private final PaymentAPIClient paymentAPIClient;
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        return toTransactionResponse(transactionRepository.save(toTransaction(transactionRequest)));
    }

    @Override
    public TransactionResponse editTransaction(UUID transactionId, TransactionRequest transactionRequest) {

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new TransactionNotFoundException(transactionId));
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setTransactionNumber(transactionRequest.getTransactionNumber());
        transaction.setDate(transactionRequest.getDate());
        transaction.setType(transactionRequest.getType());

        return toTransactionResponse(transactionRepository.save(transaction));
    }

    @Override
    public String deleteTransaction(UUID transactionId) {
        if (!transactionRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException(transactionId);
        }

        transactionRepository.deleteById(transactionId);
        return "Transaction with ID: " + transactionId + " deleted successfully";
    }

    @Override
    public List<TransactionResponse> getAllTransactions(UUID supplierId) {

        ResponseEntity<List<TransactionRequest>> invoiceResponses = supplierInvoiceAPIClient.getAllTransactionsBySupplier(supplierId);
        ResponseEntity<List<TransactionRequest>> paymentResponses = paymentAPIClient.getAllBySupplier(supplierId);

        List<TransactionResponse> customTransactions = transactionRepository.findAllBySupplierId(supplierId).stream()
                .map(this::toTransactionResponse).toList();


        List<TransactionResponse> paymentTransactions = Objects.requireNonNull(paymentResponses.getBody()).stream()
                .map(payment -> toTransactionResponse(payment, "recipe"))  // Asigna "recipe" a las transacciones de pagos
                .toList();

        List<TransactionResponse> invoiceTransactions = Objects.requireNonNull(invoiceResponses.getBody()).stream()
                .map(invoice -> toTransactionResponse(invoice, "invoice"))  // Asigna "invoice" a las transacciones de facturas
                .toList();

        List<TransactionResponse> allTransactions = new ArrayList<>(invoiceTransactions);
        allTransactions.addAll(paymentTransactions);
        allTransactions.addAll(customTransactions);

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
                .taxSave(transactionRequest.isTaxSave())
                .build();
    }

    private Transaction toTransaction(TransactionRequest transactionRequest) {
        return Transaction.builder()
                .type(transactionRequest.getType())
                .date(transactionRequest.getDate())
                .transactionNumber(transactionRequest.getTransactionNumber())
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .supplierId(transactionRequest.getSupplierId())
                .taxSave(transactionRequest.isTaxSave())
                .build();
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .type(transaction.getType())
                .date(transaction.getDate())
                .transactionNumber(transaction.getTransactionNumber())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .taxSave(transaction.isTaxSave())
                .build();
    }
}
