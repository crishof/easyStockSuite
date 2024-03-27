package com.crishof.productsv.apiCient;

import com.crishof.productsv.dto.PriceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "price-sv", url = "http://localhost:9006")
public interface PriceApiClient {

    @GetMapping("/price/getById/{id}")
    ResponseEntity<?> getById(@PathVariable("id") UUID id);

    @PostMapping("/price/save")
    ResponseEntity<?> save(@RequestBody PriceRequest priceRequest);
}
