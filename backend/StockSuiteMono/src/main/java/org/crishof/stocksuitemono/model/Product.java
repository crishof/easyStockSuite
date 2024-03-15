package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.dto.ProductRequest;

import java.util.ArrayList;
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
    @ManyToOne(targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    private String supplierCode;
    private String code;
    private String supplierModel;
    private String model;
    private String supplierDescription;
    private String description;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;
    @Embedded
    private Price price;
    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();
    @OneToMany
    private List<Image> images;
    @Embedded
    private Dimensions dimensions;
    private boolean hidden = false;

    public Product(ProductRequest productRequest) {
        this.code = productRequest.getCode();
        this.model = productRequest.getModel();
        this.description = productRequest.getDescription();
    }
}

/*
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String image;
    private String brand;
    private String color;
    private String size;
    private String condition;
    private String material;
    private String model;
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


