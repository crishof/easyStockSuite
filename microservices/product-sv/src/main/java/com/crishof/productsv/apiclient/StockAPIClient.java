package com.crishof.productsv.apiclient;

import com.crishof.productsv.dto.StockRequest;
import com.crishof.productsv.dto.StockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "stock-sv", url = "http://localhost:9009/stock")
public interface StockAPIClient {

    @PostMapping("/save")
    UUID save(@RequestBody StockRequest stockRequest);

    @GetMapping("/getTotalStockForProduct")
    ResponseEntity<?> getTotalStockForProduct(@RequestParam("stockIdList") List<UUID> stockIdList);

    @GetMapping("/getAllProductStocks")
    List<StockResponse> getAllProductStocks(@RequestParam List<UUID> stockIds);

    @PutMapping("/updateQuantity/{stockId}")
    ResponseEntity<?> updateQuantity(@PathVariable("stockId") UUID stockId, @RequestBody StockRequest stockRequest);
}
