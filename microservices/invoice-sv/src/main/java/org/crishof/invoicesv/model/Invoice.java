package org.crishof.invoicesv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tbl_customer_invoice")
public class Invoice {

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    List<InvoiceItem> invoiceItems = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID customerId;
    private UUID branchId;
    private LocalDate invoiceDate;
    private String invoiceType;
    private String invoiceNumber;
    private String packingListNumber;
    private boolean taxSave;

    private String observations;

    private double subtotal1;
    private Double discount;
    private double interest;
    private double subtotal2;

    private double netValue21;
    private double vat21;
    private double netValue105;
    private double vat105;
    private double netValue27;
    private double vat27;
    private double netValue0;

    private double withholdingVat;
    private double withholdingSuss;
    private double withholdingGrossReceiptsTax;
    private double withholdingIncome;
    private double stateTax;
    private double localTax;

    private double rounding;

    private double totalPrice;


}
