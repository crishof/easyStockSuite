package com.crishof.brandsv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "product-sv", url = "http://localhost:9001")
public interface ProductApiClient {

    @GetMapping("/product/checkProductsByBrand/{brandId}")
    ResponseEntity<Boolean> checkProductsByBrand(@PathVariable UUID brandId);
}
