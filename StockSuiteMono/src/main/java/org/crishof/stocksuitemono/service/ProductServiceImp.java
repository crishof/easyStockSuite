package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findtById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {

        Product product1 = this.findtById(id);

        product1.setCode(product.getCode());
        product1.setModel(product.getModel());
        product1.setDescription(product.getDescription());
        product1.setBrandId(product.getBrandId());
        product1.setCategoryId(product.getCategoryId());

        return productRepository.save(product1);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
