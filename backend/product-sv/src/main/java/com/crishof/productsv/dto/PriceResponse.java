package com.crishof.productsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {

    private double purchasePrice;
    private double sellingPrice;
    private double webSellingPrice;
    private double taxRate;

}
