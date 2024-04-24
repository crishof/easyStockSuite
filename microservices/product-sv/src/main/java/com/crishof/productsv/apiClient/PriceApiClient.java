package com.crishof.productsv.apiClient;

import com.crishof.productsv.dto.PriceRequest;
import com.crishof.productsv.dto.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "price-sv", url = "http://localhost:9006")
public interface PriceApiClient {

    @GetMapping("/price/getById/{id}")
    PriceResponse getById(@PathVariable("id") UUID id);

    @PostMapping("/price/save")
    ResponseEntity<?> save(@RequestBody PriceRequest priceRequest);

    @PutMapping("/update/{priceId}")
    ResponseEntity<?> update(@PathVariable("priceId") UUID priceId, @RequestBody PriceRequest priceRequest);
}
