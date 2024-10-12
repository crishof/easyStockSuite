package com.crishof.transactionsv.controller;

import com.crishof.transactionsv.dto.TransactionRequest;
import com.crishof.transactionsv.dto.TransactionResponse;
import com.crishof.transactionsv.exception.TransactionNotFoundException;
import com.crishof.transactionsv.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public ResponseEntity<TransactionResponse> save(@RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.createTransaction(transactionRequest));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<TransactionResponse> edit(@RequestBody TransactionRequest transactionRequest, @PathVariable(name = "id") UUID transactionId) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.editTransaction(transactionId, transactionRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") UUID transactionId) {
        String message;
        try {
            message = transactionService.deleteTransaction(transactionId);
        } catch (TransactionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("transaction with id " + transactionId + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
