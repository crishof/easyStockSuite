package com.crishof.transactionsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tbl_transaction")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;
    private String type;
    private LocalDate date;
    private String transactionNumber;
    private double amount;
    private String description;
    private UUID supplierId;
    private boolean taxSave;
}
