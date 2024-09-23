package com.crishof.paymentsv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_payment")
@Builder
public class Payment {

    @Id
    @GeneratedValue
    private UUID paymentId;
    private LocalDate paymentDate;
    private String paymentNumber;
    private double amount;
    private String currency;
    private boolean taxSave;
    private String paymentMethod;
    private String paymentReference;
    private String status;
    private UUID supplierId;
    private UUID invoiceId;
    private double exchangeRate;
    private String remarks;
    private String description;

}
