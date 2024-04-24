package com.crishof.productsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierInvoiceItem {

    private UUID id;
    private Integer quantity;
    private Double price;
    private Double taxRate;
    private Double discountRate;

}
