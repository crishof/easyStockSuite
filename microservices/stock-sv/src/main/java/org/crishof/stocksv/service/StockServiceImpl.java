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

        Stock stock = stockRepository.findByProductIdAndBranchIdAndLocationId(stockRequest.getProductId(), stockRequest.getBranchId(), stockRequest.getLocationId());

        if (stock == null) {
            stock = new Stock();
        }
        stock.setMin(stockRequest.getMin());
        stock.setMax(stockRequest.getMax());
        stock.setProductId(stockRequest.getProductId());
        stock.setQuantity(stock.getQuantity() + stockRequest.getQuantity());
        stock.setBranchId(stockRequest.getBranchId());
        stock.setLocationId(stockRequest.getLocationId());

        return this.toStockResponse(stockRepository.save(stock));
    }

    @Override
    public StockResponse updateQuantity(UUID stockId, StockRequest stockRequest) {

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(stockId));

        stock.setQuantity(stock.getQuantity() + stockRequest.getQuantity());
        return this.toStockResponse(stockRepository.save(stock));

    }

    @Override
    public StockResponse getStockById(UUID stockId) {
        return this.toStockResponse(stockRepository.findById(stockId).orElseThrow(() -> new StockNotFoundException(stockId)));
    }

    @Override
    public int getTotalStockForProduct(List<UUID> stockIdList) {

        int totalStock = 0;
        for (UUID stockId : stockIdList) {
            totalStock += getStockById(stockId).getQuantity();
        }
        return totalStock;
    }

    @Override
    public List<StockResponse> getAllProductStocks(List<UUID> stockIds) {

        return stockRepository.findAllById(stockIds).stream()
                .map(this::toStockResponse)
                .toList();
    }

    private StockResponse toStockResponse(Stock stock) {

        return StockResponse.builder()
                .id(stock.getId())
                .branchId(stock.getBranchId())
                .locationId(stock.getLocationId())
                .quantity(stock.getQuantity())
                .max(stock.getMax())
                .min(stock.getMin())
                .build();
    }
}