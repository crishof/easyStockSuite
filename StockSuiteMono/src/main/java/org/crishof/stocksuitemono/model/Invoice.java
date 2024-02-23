package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.enums.TransactionType;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_invoice")
public class Invoice {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tbl_invoice_product", joinColumns = @JoinColumn(name = "invoice_id"))
    @MapKeyColumn(name = "product_id")
    Map<UUID, Integer> productList;
    @Id
    @GeneratedValue
    @Column(name = "invoice_id")
    private UUID id;
    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private UUID entityId;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    public Invoice(InvoiceRequest invoiceRequest) {
        this.invoiceNumber = invoiceRequest.getInvoiceNumber();
        this.issueDate = invoiceRequest.getIssueDate();
        this.receptionDate = invoiceRequest.getReceptionDate();
        this.dueDate = invoiceRequest.getReceptionDate();
        this.entityId = invoiceRequest.getEntityId();
        this.transactionType = invoiceRequest.getTransactionType();
    }

    public void updateFromRequest(InvoiceRequest invoiceRequest) {
        this.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
        this.setDueDate(invoiceRequest.getDueDate());
        this.setReceptionDate(invoiceRequest.getReceptionDate());
        this.setIssueDate(invoiceRequest.getIssueDate());
        this.setProductList(invoiceRequest.getProductList());
        this.setEntityId(invoiceRequest.getEntityId());
    }


}
