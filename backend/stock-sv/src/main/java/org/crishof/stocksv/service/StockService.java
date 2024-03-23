package org.crishof.stocksv.service;

import java.util.UUID;

public interface StockService {

    void save(Stock stock);

    Stock save(Product product, int quantity);

    int getStockForProduct(UUID productID);
}
