package org.crishof.invoicesv.apiclient;

import org.crishof.invoicesv.dto.CustomerRequest;
import org.crishof.invoicesv.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "customer-sv", url = "http://localhost:9012/customer")
public interface CustomerApiClient {

    @GetMapping("/getById/{id}")
    ResponseEntity<CustomerResponse> getById(@PathVariable("id") UUID id);

    @PostMapping("/save")
    ResponseEntity<CustomerResponse> save(@RequestBody CustomerRequest customerRequest);
}
