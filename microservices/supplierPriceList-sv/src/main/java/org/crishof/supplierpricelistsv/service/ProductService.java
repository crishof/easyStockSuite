package org.crishof.supplierpricelistsv.service;

import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> readExcel(MultipartFile file);

    void save(Product product);

    Product findProductByBrandAndCodeAndSupplierId(String brand, String code, UUID supplierId);

    List<ProductResponse> getAllByFilter(UUID supplierId, String brand, String filter);
}
