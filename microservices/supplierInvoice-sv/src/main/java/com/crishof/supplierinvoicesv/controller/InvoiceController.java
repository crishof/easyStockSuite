package com.crishof.supplierinvoicesv.controller;

import com.crishof.supplierinvoicesv.dto.InvoiceRequest;
import com.crishof.supplierinvoicesv.dto.InvoiceResponse;
import com.crishof.supplierinvoicesv.dto.TransactionResponse;
import com.crishof.supplierinvoicesv.repository.InvoiceRepository;
import com.crishof.supplierinvoicesv.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody InvoiceRequest invoiceRequest) {

        try {
            InvoiceResponse invoiceResponse = invoiceService.save(invoiceRequest);
            return ResponseEntity.ok().body(Map.of("message", "Invoice saved successfully " + invoiceResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to save invoice"));
        }
    }

    @GetMapping("/getAllTransactionsBySupplier/{supplierId}")
    public ResponseEntity<List<TransactionResponse>> getAllTransactionsBySupplier(@PathVariable UUID supplierId) {
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getAllTransactionsBySupplier(supplierId));
    }
}
