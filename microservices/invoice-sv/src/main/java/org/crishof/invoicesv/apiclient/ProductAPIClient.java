package org.crishof.invoicesv.apiclient;

import org.crishof.invoicesv.dto.InvoiceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "product-sv", url = "http://localhost:9001/product")
public interface ProductAPIClient {

    @GetMapping("/getById/{id}")
    ResponseEntity<?> getById(@PathVariable("id") UUID id);

    @PutMapping("/customerInvoice")
    String updateProductStock(@RequestBody InvoiceUpdateRequest invoiceUpdateRequest);
}
