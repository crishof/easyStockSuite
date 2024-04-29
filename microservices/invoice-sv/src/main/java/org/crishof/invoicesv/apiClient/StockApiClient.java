package org.crishof.invoicesv.apiClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@FeignClient(name = "stock-sv", url = "http://localhost:9009")
public interface StockApiClient {

    @GetMapping("/getStockById")
    ResponseEntity<?> getStockById(UUID stockId);
}
