package org.crishof.invoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.invoicesv.enums.TransactionType;
import org.crishof.invoicesv.model.Invoice;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private UUID id;
    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private UUID entityId;
    private TransactionType transactionType;

    public InvoiceResponse(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceNumber = invoice.getInvoiceNumber();
        this.issueDate = invoice.getIssueDate();
        this.receptionDate = invoice.getReceptionDate();
        this.dueDate = invoice.getDueDate();
        this.entityId = invoice.getEntityId();
        this.transactionType = invoice.getTransactionType();
    }
}
