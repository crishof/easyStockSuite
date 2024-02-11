package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Stock;
import org.crishof.stocksuitemono.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Override
    public void save(Stock stock) {

    }

    @Override
    public Stock save(Product product, int quantity) {

        return stockRepository.save(new Stock(quantity, product));
    }

    @Override
    public int getStockForProduct(Product product) {
        int total = 0;
        for (Stock stock : product.getStocks()) {
            total += stock.getQuantity();
        }
        return total;
    }
}
