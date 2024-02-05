package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductResponse> getAll();

    ProductResponse getById(UUID id);

    ProductResponse save(ProductRequest productRequest);

    ProductResponse update(UUID id, ProductRequest productRequest);

    void deleteById(UUID id);
}