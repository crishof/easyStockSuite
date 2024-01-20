package com.crishof.productsv.service;

import com.crishof.productsv.model.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);

    List<Product> findAll();

    Product findById(Long id);

    void update(Long id, Product product);

    void deleteById(Long id);

    void save(String code, String model, String description, Long brandId);
}
