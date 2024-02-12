package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.enums.TransactionType;
import org.crishof.stocksuitemono.model.Product;

import java.time.LocalDate;
import java.util.List;
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
    private List<Product> productList;
    private Map<UUID, String> productSaleList;
    private List<Integer> quantities;
    private TransactionType transactionType;
}
