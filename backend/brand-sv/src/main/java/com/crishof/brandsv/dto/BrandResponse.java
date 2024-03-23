package com.crishof.brandsv.dto;

import com.crishof.brandsv.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {

    private UUID id;
    private String name;
    private String imageUrl;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.imageUrl = brand.getImageUrl();
    }
}
