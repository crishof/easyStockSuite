package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String code;
    private String model;
    private String description;
    private Long BrandId;
    private Long CategoryId;

}
