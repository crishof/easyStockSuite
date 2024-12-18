package com.crishof.productsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    private UUID id;
    private int quantity;
    private UUID branchId;
    private UUID locationId;
    private int max;
    private int min;
}
