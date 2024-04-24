package com.crishof.productsv.apiClient;

import com.crishof.productsv.dto.StockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "stock-sv", url = "http://localhost:9009")
public interface StockAPIClient {

    @PostMapping("/save")
    ResponseEntity<UUID> save(@RequestBody StockRequest stockRequest);
}
