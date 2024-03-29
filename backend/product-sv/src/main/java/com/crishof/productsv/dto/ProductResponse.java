package com.crishof.productsv.dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    private String categoryName;
    private UUID supplierId;
    private boolean hidden = false;
    @ElementCollection
    private List<UUID> imageId;
    @ElementCollection
    private List<UUID> stockIds;
    private UUID priceId;
    private UUID dimension;

//    public ProductResponse(Product product) {

//        this.id = product.getId();

//        this.brandName = productService.getBrandName(product.getBrandId());

//        this.code = product.getCode();
//        this.model = product.getModel();
//        this.description = product.getDescription();
//        this.categoryId = product.getCategoryId();
//        this.supplierId = product.getSupplierId();
//        this.hidden = product.isHidden();
//        this.imageId = product.getImageId();
//        this.stockIds = product.getStockIds();
//        this.priceId = product.getPriceId();
//        this.dimension = product.getDimensionId();
//    }
}