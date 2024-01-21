package com.crishof.productsv.service;

import com.crishof.productsv.dto.BrandDTO;
import com.crishof.productsv.model.Product;
import com.crishof.productsv.repository.BrandAPIClient;
import com.crishof.productsv.repository.ProducRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProducRepository producRepository;
    @Autowired
    BrandAPIClient brandAPIClient;

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

    @Override
    @CircuitBreaker(name = "brand-sv", fallbackMethod = "fallbackGetBrandInfo")
    @Retry(name = "brand-sv")
    public BrandDTO getBrandInfo(Long brandId) {
        BrandDTO brandDTO = brandAPIClient.getBrandInfo(brandId);

//        createException();

        return brandDTO;
    }

    public BrandDTO fallbackGetBrandInfo(Throwable throwable) {
        return new BrandDTO("ERROR Brand");
    }

    public void createException() {
        throw new IllegalArgumentException("Prueba Resilience y Circuit Breaker");
    }
}
