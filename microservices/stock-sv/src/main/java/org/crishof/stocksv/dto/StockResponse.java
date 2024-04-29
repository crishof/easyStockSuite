package org.crishof.stocksv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksv.model.Stock;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    private UUID id;
    private int quantity;
    private UUID branchId;
    private int max;
    private int min;

    public StockResponse(Stock stock) {
        this.id = stock.getId();
        this.quantity = stock.getQuantity();
        this.branchId = stock.getBranchId();
        this.max = stock.getMax();
        this.min = stock.getMin();
    }
}
