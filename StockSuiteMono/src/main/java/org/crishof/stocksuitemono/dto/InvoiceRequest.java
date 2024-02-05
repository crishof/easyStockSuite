package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.model.Product;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private Long supplierId;
    private List<Product> productList;
    private List<Integer> quantities;
}
