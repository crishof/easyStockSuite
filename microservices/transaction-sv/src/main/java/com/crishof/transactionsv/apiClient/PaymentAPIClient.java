package com.crishof.transactionsv.apiclient;

import com.crishof.transactionsv.dto.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "payment-sv", url = "http://localhost:9018")
public interface PaymentAPIClient {

    @GetMapping("/payment/getAllTransactionsBySupplier/{supplierId}")
    ResponseEntity<List<TransactionRequest>> getAllBySupplier(@PathVariable UUID supplierId);
}
