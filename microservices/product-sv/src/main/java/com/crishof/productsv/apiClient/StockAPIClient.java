package com.crishof.productsv.apiClient;

import com.crishof.productsv.dto.StockRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "stock-sv", url = "http://localhost:9009")
public interface StockAPIClient {

    @PostMapping("/stock/save")
    UUID save(@RequestBody StockRequest stockRequest);

    @GetMapping("/stock/getTotalStockForProduct")
    public ResponseEntity<?> getTotalStockForProduct(@RequestParam("stockIdList") List<UUID> stockIdList);
}
