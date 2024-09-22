package com.crishof.transactionsv.apiClient;

import com.crishof.transactionsv.dto.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "supplierInvoice-sv", url = "http://localhost:9016")
public interface SupplierInvoiceAPIClient {

    @GetMapping("/invoice/getAllTransactionsBySupplier/{supplierId}")
    ResponseEntity<List<TransactionResponse>> getAllTransactionsBySupplier(@PathVariable UUID supplierId);
}
