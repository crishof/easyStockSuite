package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String brandName;
    private String code;
    private String model;
    private String description;
    private double purchasePrice;
    private double taxRate;
    private double sellingPrice;
    private String supplier;
}
