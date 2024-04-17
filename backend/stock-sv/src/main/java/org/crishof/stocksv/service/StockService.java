package org.crishof.stocksv.service;

import org.crishof.stocksv.dto.StockRequest;
import org.crishof.stocksv.dto.StockResponse;

import java.util.List;
import java.util.UUID;

public interface StockService {

    StockResponse save(StockRequest stockRequest);

    StockResponse updateQuantity(UUID stockId, StockRequest stockRequest);

    StockResponse getStockById(UUID stockId);

    int getTotalStockForProduct(List<UUID> stockIdList);
}
