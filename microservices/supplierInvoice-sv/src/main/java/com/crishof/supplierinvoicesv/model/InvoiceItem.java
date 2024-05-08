package com.crishof.supplierinvoicesv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_invoice_item")
@Builder
public class InvoiceItem {

    @Id
    @GeneratedValue
    private UUID id;

    private Integer quantity;
    private Double price;
    private Double taxRate;
    private Double discountRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
