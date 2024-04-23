package org.crishof.supplierpricelistsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.supplierpricelistsv.dto.ProductRequest;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_supplierProduct")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id", nullable = false)
    private UUID id;
    private UUID supplierId;
    private String brand;
    private String code;
    private String model;
    private String description;
    private String category;
    private LocalDate lastUpdate;
    private double price;
    private double suggestedPrice;
    private double suggestedWebPrice;
    private String stockAvailable;
    private String barcode;
    private String currency;
    private double taxRate;

    public Product(ProductRequest productRequest) {
        this.supplierId = productRequest.getSupplierId();
        this.brand = productRequest.getBrand();
        this.code = productRequest.getCode();
        this.model = productRequest.getModel();
        this.description = productRequest.getDescription();
        this.category = productRequest.getCategory();
        this.lastUpdate = productRequest.getLastUpdate();
        this.price = productRequest.getPrice();
        this.suggestedPrice = productRequest.getSuggestedPrice();
        this.suggestedWebPrice = productRequest.getSuggestedWebPrice();
        this.stockAvailable = productRequest.getStockAvailable();
        this.barcode = productRequest.getBarcode();
        this.currency = productRequest.getCurrency();
        this.taxRate = productRequest.getTaxRate();
    }


}
