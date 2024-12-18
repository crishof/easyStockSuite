package org.crishof.stocksv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequest {

    private UUID productId;
    private int quantity;
    private UUID branchId;
    private UUID locationId;
    private int max;
    private int min;
}
