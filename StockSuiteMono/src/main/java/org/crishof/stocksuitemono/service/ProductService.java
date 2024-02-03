package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(Long id);

    ProductResponse save(ProductRequest productRequest);

    ProductResponse update(Long id, ProductRequest productRequest);

    void deleteById(Long id);
}