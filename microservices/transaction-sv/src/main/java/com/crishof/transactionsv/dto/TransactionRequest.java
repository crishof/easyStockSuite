package com.crishof.transactionsv.dto;

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
public class TransactionRequest {

    private UUID transactionId;
    private String type;
    private LocalDate date;
    private String transactionNumber;
    private double amount;
    private boolean taxSave;
    private String description;
    private UUID supplierId;
}
