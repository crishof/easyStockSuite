package com.crishof.productsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_product")
public class Product {

    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String model;
    private String description;
    private Long brandId;

    public Product(String code, String model, String description, Long brandId) {
        this.code = code;
        this.model = model;
        this.description = description;
        this.brandId = brandId;
    }
}
