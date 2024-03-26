package com.crishof.productsv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "category-sv", url = "http://localhost:9003")
public interface CategoryAPIClient {

    @GetMapping("/category/getIdByName")
    ResponseEntity<?> getIdByName(@RequestParam String categoryName);
}
