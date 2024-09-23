package com.crishof.paymentsv.service;

import com.crishof.paymentsv.dto.PaymentRequest;
import com.crishof.paymentsv.dto.PaymentResponse;
import com.crishof.paymentsv.dto.TransactionResponse;
import com.crishof.paymentsv.model.Payment;
import com.crishof.paymentsv.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<PaymentResponse> savePayment(PaymentRequest paymentRequest) {
        Payment savedPayment = paymentRepository.save(toPayment(paymentRequest));
        return ResponseEntity.ok(toPaymentResponse(savedPayment));
    }

    @Override
    public ResponseEntity<List<PaymentResponse>> getAllBySupplier(UUID supplierId) {

        return ResponseEntity.ok(paymentRepository.findAllBySupplierId(supplierId).stream()
                .map(this::toPaymentResponse).toList());
    }

    @Override
    public List<TransactionResponse> getAllTransactionsBySupplier(UUID supplierId) {
        return paymentRepository.findAllBySupplierId(supplierId).stream().map(this::toTransactionResponse).toList();
    }

    private TransactionResponse toTransactionResponse(Payment payment) {
        return TransactionResponse.builder()
                .transactionId(payment.getPaymentId())
                .date(payment.getPaymentDate())
                .type("payment.getType()")
                .transactionNumber(payment.getPaymentNumber())
                .taxSave(payment.isTaxSave())
                .description(payment.getDescription())
                .amount(payment.getAmount())
                .build();
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .description(payment.getDescription())
                .paymentDate(payment.getPaymentDate())
                .paymentMethod(payment.getPaymentMethod())
                .paymentReference(payment.getPaymentReference())
                .status(payment.getStatus())
                .supplierId(payment.getSupplierId())
                .invoiceId(payment.getInvoiceId())
                .exchangeRate(payment.getExchangeRate())
                .remarks(payment.getRemarks())
                .paymentId(payment.getPaymentId())
                .build();
    }

    private Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amount(paymentRequest.getAmount())
                .currency(paymentRequest.getCurrency())
                .description(paymentRequest.getDescription())
                .paymentDate(paymentRequest.getPaymentDate())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentReference(paymentRequest.getPaymentReference())
                .status(paymentRequest.getStatus())
                .supplierId(paymentRequest.getSupplierId())
                .invoiceId(paymentRequest.getInvoiceId())
                .exchangeRate(paymentRequest.getExchangeRate())
                .remarks(paymentRequest.getRemarks())
                .build();

    }
}
