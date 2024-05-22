package org.crishof.pricesv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.pricesv.model.Price;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse {

    private double purchasePrice;
    private double sellingPrice;
    private double webSellingPrice;
    private double taxRate;

    public PriceResponse(Price price) {
        this.purchasePrice = price.getPurchasePrice();
        this.sellingPrice = price.getSellingPrice();
        this.webSellingPrice = price.getWebSellingPrice();
        this.taxRate = price.getTaxRate();
    }
}
