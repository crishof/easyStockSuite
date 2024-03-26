package com.crishof.productsv.apiCient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "brand-sv", url = "http://localhost:9002")
public interface BrandAPIClient {

    @GetMapping("/brand/getIdByName")
    ResponseEntity<?> getIdByName(@RequestParam String brandName);


}
