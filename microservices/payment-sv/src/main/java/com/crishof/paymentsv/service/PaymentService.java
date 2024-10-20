package com.crishof.paymentsv.service;

import com.crishof.paymentsv.dto.PaymentRequest;
import com.crishof.paymentsv.dto.PaymentResponse;
import com.crishof.paymentsv.dto.TransactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    ResponseEntity<PaymentResponse> savePayment(@RequestBody PaymentRequest paymentRequest);

    ResponseEntity<List<PaymentResponse>> getAllBySupplier(UUID supplierId);

    List<TransactionResponse> getAllTransactionsBySupplier(UUID supplierId);
}
