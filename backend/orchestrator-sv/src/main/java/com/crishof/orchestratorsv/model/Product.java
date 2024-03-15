package com.crishof.orchestratorsv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private Long id;
    private String code;
    private String model;
    private String description;
    private Long brandId;
    private Long categoryId;
    private Long supplierId;
}
