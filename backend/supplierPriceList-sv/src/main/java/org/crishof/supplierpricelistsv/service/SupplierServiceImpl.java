package org.crishof.supplierpricelistsv.service;

import org.crishof.supplierpricelistsv.model.Product;
import org.crishof.supplierpricelistsv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<String> getBrandsBySupplier(UUID supplierId) {

        List<Product> products = productRepository.findAllBySupplierId(supplierId);

        List<String> brands = products.stream()
                .map(Product::getBrand)
                .distinct()
                .toList();

        System.out.println("brands = " + brands);
        return brands;
    }

    @Override
    public List<String> getAllBrands() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(Product::getBrand)
                .distinct()
                .toList();
    }
}
