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
public class TransactionResponse {

    private UUID transactionId;
    private LocalDate invoiceDate;
    private String invoiceType;
    private String invoiceNumber;
    private boolean taxSave;
    private String observations;
    private double totalPrice;
}
