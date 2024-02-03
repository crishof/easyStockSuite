package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.model.Brand;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {

    private Long id;
    private String name;

    public BrandResponse(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
    }
}
