package com.crishof.categorysv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "product-sv", url = "http://localhost:9001")
public interface ProductApiClient {

    @PutMapping("/product/removeCategory")
    ResponseEntity<String> removeCategory(@RequestParam UUID categoryId);
}
