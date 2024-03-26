package com.crishof.productsv.service;

import com.crishof.productsv.dto.ProductRequest;
import com.crishof.productsv.dto.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductResponse> getAll();

    ProductResponse getById(UUID id);

    ProductResponse save(ProductRequest productRequest);

    ProductResponse update(UUID id, ProductRequest productRequest);

    void deleteById(UUID id);

    List<ProductResponse> getAllByFilter(String filter);

    List<ProductResponse> getAllByFilterAndStock(String filter);


}
