package com.crishof.transactionsv.controller;

import com.crishof.transactionsv.dto.TransactionResponse;
import com.crishof.transactionsv.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/getAllBySupplier/{supplierId}")
    public ResponseEntity<List<TransactionResponse>> getAllBySupplier(@PathVariable UUID supplierId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAllTransactions(supplierId));
    }
}
