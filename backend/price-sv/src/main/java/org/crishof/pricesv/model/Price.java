package org.crishof.pricesv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.pricesv.dto.PriceRequest;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_price")
public class Price {
    @Id
    @Column(name = "price_id")
    @GeneratedValue
    private UUID uuid;
    private double purchasePrice = 0.0;
    private double sellingPrice = 0.0;
    private double taxRate = 0.0;

    public Price(PriceRequest priceRequest) {
        this.purchasePrice = priceRequest.getPurchasePrice();
        this.sellingPrice = priceRequest.getSellingPrice();
        this.taxRate = priceRequest.getTaxRate();
    }
}
