package com.crishof.productsv.service;

import com.crishof.productsv.model.Product;
import com.crishof.productsv.repository.ProducRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProducRepository producRepository;

    @Override
    public void save(Product product) {
        producRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return producRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return producRepository.findById(id).orElse(null);
    }

    @Override
    public void update(Long id, Product product) {

        Product product1 = this.findById(id);

        product1.setCode(product.getCode());
        product1.setModel(product.getModel());
        product1.setDescription(product.getDescription());

        producRepository.save(product1);
    }

    @Override
    public void deleteById(Long id) {
        producRepository.deleteById(id);
    }

    @Override
    public void save(String code, String model, String description, Long brandId) {

        Product product = new Product(code, model, description, brandId);

        producRepository.save(product);
    }
}
