package org.crishof.invoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductsRequest {

    private UUID id;
    private String brandName;
    private String code;
    private String model;
    private String description;
    private String categoryName;
    private UUID supplierId;
    private UUID branchId;
    private int quantity;
    private Double price;
    private Double taxRate;
    private Double discountRate;
    private Double discountAmount;
    private Double interestRate;
    private Double interestAmount;
}
