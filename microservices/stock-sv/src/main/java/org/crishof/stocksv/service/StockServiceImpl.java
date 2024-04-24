package org.crishof.stocksv.service;

import lombok.RequiredArgsConstructor;
import org.crishof.stocksv.dto.StockRequest;
import org.crishof.stocksv.dto.StockResponse;
import org.crishof.stocksv.exception.StockNotFoundException;
import org.crishof.stocksv.model.Stock;
import org.crishof.stocksv.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public StockResponse save(StockRequest stockRequest) {
        Stock stock = new Stock();
        stock.setMin(stockRequest.getMin());
        stock.setMax(stockRequest.getMax());
        stock.setQuantity(stockRequest.getQuantity());
        stock.setBranchId(stockRequest.getBranchId());

        return new StockResponse(stockRepository.save(stock));
    }

    @Override
    public StockResponse updateQuantity(UUID stockId, StockRequest stockRequest) {

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));

        stock.setQuantity(stockRequest.getQuantity());
        return new StockResponse(stockRepository.save(stock));

    }

    @Override
    public StockResponse getStockById(UUID stockId) {

        return new StockResponse(stockRepository.getReferenceById(stockId));
    }

    @Override
    public int getTotalStockForProduct(List<UUID> stockIdList) {

        int totalStock = 0;
        for (UUID stockId : stockIdList) {
            totalStock += getStockById(stockId).getQuantity();
        }
        return totalStock;
    }
}