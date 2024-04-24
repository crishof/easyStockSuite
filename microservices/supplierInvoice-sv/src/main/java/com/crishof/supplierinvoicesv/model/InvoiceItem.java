package com.crishof.supplierinvoicesv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice_item")
public class InvoiceItem {

    @Id
    private UUID id;

    private Integer quantity;
    private Double price;
    private Double taxRate;
    private Double discountRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
