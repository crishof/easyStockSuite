package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Stock;

import java.util.UUID;

public interface StockService {

    void save(Stock stock);

    Stock save(Product product, int quantity);

    int getStockForProduct(UUID productID);
}
