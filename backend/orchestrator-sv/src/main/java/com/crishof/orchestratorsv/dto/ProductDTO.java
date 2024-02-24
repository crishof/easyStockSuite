package com.crishof.orchestratorsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String code;
    private String brand;
    private String model;
    private String description;
    private String category;
    private String supplier;
}