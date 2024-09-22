package com.crishof.paymentsv.controller;

import com.crishof.paymentsv.dto.PaymentRequest;
import com.crishof.paymentsv.dto.PaymentResponse;
import com.crishof.paymentsv.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/save")
    public ResponseEntity<PaymentResponse> savePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.savePayment(paymentRequest);
    }

    @GetMapping("/getAllBySupplier/{supplierId}")
    public ResponseEntity<List<PaymentResponse>> getAllBySupplier(@PathVariable UUID supplierId) {
        return paymentService.getAllBySupplier(supplierId);
    }

    @GetMapping("/test")
    public String test() {
        return "test OK";
    }
}
