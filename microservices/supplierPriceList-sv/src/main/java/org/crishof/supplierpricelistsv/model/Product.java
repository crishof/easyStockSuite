package org.crishof.supplierpricelistsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_supplierProduct")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id", nullable = false)
    private UUID id;
    private UUID supplierId;
    private String brand;
    private String code;
    private String model;
    private String description;
    private String category;
    private LocalDate lastUpdate;
    private double price;
    private double suggestedPrice;
    private double suggestedWebPrice;
    private String stockAvailable;
    private String barcode;
    private String currency;
    private double taxRate;
}
