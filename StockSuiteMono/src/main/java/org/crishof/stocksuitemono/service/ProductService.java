package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findtById(Long id);

    void save(ProductRequest product);

    Product update(Long id, Product product);

    void deleteById(Long id);
}