package com.crishof.supplierinvoicesv.controller;

import com.crishof.supplierinvoicesv.dto.InvoiceRequest;
import com.crishof.supplierinvoicesv.dto.InvoiceResponse;
import com.crishof.supplierinvoicesv.repository.InvoiceRepository;
import com.crishof.supplierinvoicesv.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<InvoiceResponse> save(@RequestBody InvoiceRequest invoiceRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.save(invoiceRequest));
    }
}
