package com.crishof.productsv.apiClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "supplier-sv", url = "http://localhost:9004")
public interface SupplierAPIClient {

    @GetMapping("/supplier/getIdByName")
    ResponseEntity<?> getIdByName(@RequestParam String supplierName);
}
