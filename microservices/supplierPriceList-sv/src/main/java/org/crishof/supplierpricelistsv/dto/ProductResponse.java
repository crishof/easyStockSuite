package org.crishof.supplierpricelistsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

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