package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Stock;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private UUID id;
    private String brandName;
    private String code;
    private String model;
    private String description;
    private double purchasePrice;
    private double taxRate;
    private double sellingPrice;
    private int stock;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.brandName = product.getBrand() != null ? new BrandResponse(product.getBrand()).getName() : null;
        this.code = product.getCode();
        this.model = product.getModel();
        this.description = product.getDescription();
        this.purchasePrice = product.getPrice().getPurchasePrice();
        this.taxRate = product.getPrice().getTaxRate();
        this.sellingPrice = product.getPrice().getSellingPrice();
        this.stock = (int) product.getStocks().stream().mapToDouble(Stock::getQuantity).sum();
    }
}
