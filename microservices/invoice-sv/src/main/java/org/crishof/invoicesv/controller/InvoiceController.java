package org.crishof.invoicesv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.invoicesv.dto.ApiResponse;
import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.dto.InvoiceResponse;
import org.crishof.invoicesv.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/getAll")
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(invoiceService.getById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<InvoiceResponse>> savePurchaseInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        System.out.println("invoiceRequest = " + invoiceRequest);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(invoiceService.save(invoiceRequest)));

    }
}

