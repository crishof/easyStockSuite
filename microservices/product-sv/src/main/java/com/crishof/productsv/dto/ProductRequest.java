package com.crishof.productsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String brandName;
    private String code;
    private String model;
    private String description;
    private String categoryName;
    private double purchasePrice;
    private double taxRate;
    private double sellingPrice;
    private double webSellingPrice;
    private String barcode;
    private String currency;
    private UUID supplierId;
    private UUID supplierProductId;

    public ProductRequest(SupplierProductRequest supplierProductRequest) {

        this.supplierId = supplierProductRequest.getSupplierId();
        this.brandName = supplierProductRequest.getBrand();
        this.code = supplierProductRequest.getCode();
        this.model = supplierProductRequest.getModel();
        this.description = supplierProductRequest.getDescription();
        this.categoryName = supplierProductRequest.getCategory();
        this.purchasePrice = supplierProductRequest.getPrice();
        this.taxRate = supplierProductRequest.getTaxRate();
        this.sellingPrice = supplierProductRequest.getSuggestedPrice();
        this.webSellingPrice = supplierProductRequest.getSuggestedWebPrice();
        this.barcode = supplierProductRequest.getBarcode();
        this.currency = supplierProductRequest.getCurrency();
        this.supplierProductId = supplierProductRequest.getId();
    }
}
