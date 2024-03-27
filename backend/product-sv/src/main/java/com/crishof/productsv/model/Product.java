package com.crishof.productsv.model;

import com.crishof.productsv.dto.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id", nullable = false)
    private UUID id;
    private UUID brandId;
    private String supplierCode;
    private String code;
    private String supplierModel;
    private String model;
    private String supplierDescription;
    private String description;
    private UUID categoryId;
    private UUID supplierId;
    private boolean hidden = false;
    @ElementCollection
    private List<UUID> imageId;
    @ElementCollection
    private List<UUID> stockIds;
    private UUID priceId;
    private UUID dimension;

    public Product(ProductRequest productRequest) {
        this.code = productRequest.getCode();
        this.model = productRequest.getModel();
        this.description = productRequest.getDescription();
    }
}

/*

    private String color;
    private String condition;
    private String material;
    private String warranty;

    private String sku;
    private String ean;
    private String upc;
    private String mpn;
    private String gtin;
    private String isbn;
    private String asin;
    private String ean13;
    private String ean8;
    private String upc12;
    private String upc14;
    private String mfg;
    private String mfgDate;
    private String expDate;

    private String country;
    private String state;
    private String city;
    private String zip;
    private String address;
    private String phone;
    private String email;
    private String website;

    private String notes;
    private String status;
    private String createdBy;
    private String updatedBy;
*/