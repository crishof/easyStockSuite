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
    private UUID supplierProductId;
    private boolean hidden;
    @ElementCollection
    private List<UUID> imageId;
    @ElementCollection
    private List<StockResponse> stockResponses;
    private PriceResponse priceResponse;
    private UUID dimension;

}