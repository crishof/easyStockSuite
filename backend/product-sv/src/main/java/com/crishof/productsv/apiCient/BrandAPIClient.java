package com.crishof.productsv.apiCient;

import com.crishof.productsv.dto.BrandRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "brand-sv", url = "http://localhost:9002")
public interface BrandAPIClient {

    @GetMapping("/brand/getIdByName")
    ResponseEntity<?> getIdByName(@RequestParam String brandName);

    @GetMapping("/brand/getNameById")
    ResponseEntity<String> getNameById(@RequestParam UUID uuid);

    @PostMapping("/brand/save")
    ResponseEntity<?> save(@RequestBody BrandRequest brandRequest);

    @GetMapping("/brand/getAllIdByFilter")
    ResponseEntity<?> getAllIdByFilter(@RequestParam String filter);


}
