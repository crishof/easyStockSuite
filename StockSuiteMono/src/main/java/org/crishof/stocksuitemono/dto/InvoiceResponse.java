package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.model.Invoice;
import org.crishof.stocksuitemono.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private UUID id;
    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private UUID supplierId;
    private List<ProductResponse> productList;
    private List<Integer> quantities;

    public InvoiceResponse(Invoice invoice) {
        this.id = invoice.getId();
        this.invoiceNumber = invoice.getInvoiceNumber();
        this.issueDate = invoice.getIssueDate();
        this.receptionDate = invoice.getReceptionDate();
        this.dueDate = invoice.getDueDate();
        this.supplierId = invoice.getSupplierId();
        this.productList = invoice.getProductList().stream()
                .map(ProductResponse::new).collect(Collectors.toList());
        this.quantities = invoice.getQuantities();
    }
}
