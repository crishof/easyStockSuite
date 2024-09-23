package com.crishof.transactionsv.service;

import com.crishof.transactionsv.apiClient.PaymentAPIClient;
import com.crishof.transactionsv.apiClient.SupplierInvoiceAPIClient;
import com.crishof.transactionsv.dto.PaymentResponse;
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
        // Obtén las respuestas de facturas e ingresos
        ResponseEntity<List<TransactionResponse>> invoiceResponses = supplierInvoiceAPIClient.getAllTransactionsBySupplier(supplierId);
        ResponseEntity<List<PaymentResponse>> paymentResponses = paymentAPIClient.getAllBySupplier(supplierId);

        // Mapea las respuestas de pagos a TransactionResponse
        List<TransactionResponse> paymentTransactions = Objects.requireNonNull(paymentResponses.getBody()).stream()
                .map(this::toTransactionResponse)
                .toList();

        // Combina ambas listas (facturas y pagos)
        List<TransactionResponse> allTransactions = new ArrayList<>(Objects.requireNonNull(invoiceResponses.getBody()));
        allTransactions.addAll(paymentTransactions);

        // Ordena las transacciones por fecha
        return allTransactions.stream()
                .sorted(Comparator.comparing(TransactionResponse::getInvoiceDate))  // Usa el método que retorna la fecha
                .toList();
    }

    private TransactionResponse toTransactionResponse(PaymentResponse paymentResponse) {
        return TransactionResponse.builder()
                .transactionId(paymentResponse.getPaymentId())
                .invoiceDate(paymentResponse.getPaymentDate())
                .invoiceType(paymentResponse.getDescription()) // o "Pago" si quieres especificar
                .totalPrice(paymentResponse.getAmount())
                .observations(paymentResponse.getRemarks())
                .build();
    }
}
