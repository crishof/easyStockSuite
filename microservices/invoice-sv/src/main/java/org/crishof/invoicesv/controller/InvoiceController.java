package org.crishof.invoicesv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.dto.InvoiceResponse;
import org.crishof.invoicesv.exception.InvoiceNotFoundException;
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
        try {
            return ResponseEntity.ok(new ApiResponse<>(invoiceService.getById(id)));
        } catch (InvoiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<InvoiceResponse>> savePurchaseInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        System.out.println("invoiceRequest = " + invoiceRequest);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(invoiceService.save(invoiceRequest)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error saving invoice: " + e.getMessage()));
        }
    }
}

