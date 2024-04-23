package org.crishof.invoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.invoicesv.enums.TransactionType;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private UUID entityId;
    private Map<UUID, Integer> productList;
    private TransactionType transactionType;
}
