package com.crishof.supplierinvoicesv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_invoice")
public class Invoice {

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InvoiceItem> productRequests;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate receivedDate;
    private Double discount;
    private Double total;
    private UUID supplierId;

}
