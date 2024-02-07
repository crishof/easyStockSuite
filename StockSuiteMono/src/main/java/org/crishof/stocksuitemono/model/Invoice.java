package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.enums.TransactionType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_invoice")
public class Invoice {

    @Id
    @GeneratedValue
    @Column(name = "invoice_id")
    private UUID id;
    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private UUID entityId;
    @OneToMany
    @JoinColumn(name = "invoice_id")
    private List<Product> productList;
    @ElementCollection
    private List<Integer> quantities;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    public Invoice(InvoiceRequest invoiceRequest) {
        this.invoiceNumber = invoiceRequest.getInvoiceNumber();
        this.issueDate = invoiceRequest.getIssueDate();
        this.receptionDate = invoiceRequest.getReceptionDate();
        this.dueDate = invoiceRequest.getReceptionDate();
        this.entityId = invoiceRequest.getEntityId();
        this.productList = invoiceRequest.getProductList();
        this.quantities = invoiceRequest.getQuantities();
        this.transactionType = invoiceRequest.getTransactionType();
    }

}
