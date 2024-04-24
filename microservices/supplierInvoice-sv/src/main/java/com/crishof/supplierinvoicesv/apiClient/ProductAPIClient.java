package com.crishof.supplierinvoicesv.apiClient;

import com.crishof.supplierinvoicesv.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name = "product-sv", url = "http://localhost:9001")
public interface ProductAPIClient {

    @PutMapping("/invoice")
    ResponseEntity<?> updateProductStockAndPrices(List<InvoiceItem> invoiceItems);
}
