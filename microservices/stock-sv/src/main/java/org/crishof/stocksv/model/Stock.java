package org.crishof.stocksv.model;

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
    @GeneratedValue
    @Column(name = "stock_id")
    private UUID id;
    private UUID branchId;
    private UUID locationId;
    private UUID productId;
    private int quantity;
    private int max;
    private int min;

}
