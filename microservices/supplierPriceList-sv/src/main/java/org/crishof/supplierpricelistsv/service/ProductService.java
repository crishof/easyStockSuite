package org.crishof.supplierpricelistsv.service;

import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ProductNotFoundException;
import org.crishof.supplierpricelistsv.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> readExcel(MultipartFile file) throws IOException;

    void save(Product product);

    Product findProductByBrandAndCodeAndSupplierId(String brand, String code, UUID supplierId);

    List<ProductResponse> getAllByFilter(UUID supplierId, String brand, String filter);

    ProductResponse getById(UUID id) throws ProductNotFoundException;
}
