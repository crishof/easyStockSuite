package org.crishof.invoicesv.apiClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product-sv", url = "http://localhost:9001")
public interface ProductAPIClient {

    @GetMapping("/getById/{id}")
    ResponseEntity<?> getById(@PathVariable("id") UUID id);
}