package com.crishof.paymentsv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private UUID paymentId;
    private LocalDate paymentDate;
    private double amount;
    private String currency;
    private String paymentMethod;
    private String paymentReference;
    private String status;
    private UUID supplierId;
    private UUID invoiceId;
    private double exchangeRate;
    private String remarks;
    private String description;
}
