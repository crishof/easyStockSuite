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
    private String code;
    private String model;
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

    public Product(ProductRequest productRequest) {
        this.code = productRequest.getCode();
        this.model = productRequest.getModel();
        this.description = productRequest.getDescription();
    }

}


