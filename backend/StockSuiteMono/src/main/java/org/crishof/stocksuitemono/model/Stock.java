package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_stock")
public class Stock {

    @Id
    @Column(name = "stock_id")
    @GeneratedValue
    private UUID id;
    private int quantity;
    private int max;
    private int min;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Stock(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }
}
