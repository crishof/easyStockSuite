package com.crishof.productsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {

    private double purchasePrice;
    private double sellingPrice;
    private double webSellingPrice;
    private double taxRate;
    private Double discountRate;

    public PriceRequest(double purchasePrice, double sellingPrice, double webSellingPrice, double taxRate) {
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.webSellingPrice = webSellingPrice;
        this.taxRate = taxRate;
    }
}