package com.crishof.productsv.service;

import com.crishof.productsv.apiCient.BrandAPIClient;
import com.crishof.productsv.dto.ProductRequest;
import com.crishof.productsv.dto.ProductResponse;
import com.crishof.productsv.exeption.ProductNotFoundException;
import com.crishof.productsv.model.Product;
import com.crishof.productsv.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandAPIClient brandAPIClient;

    @Override
    public List<ProductResponse> getAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream().map(ProductResponse::new).toList();
    }

    @Override
    public ProductResponse getById(UUID id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return new ProductResponse(product);
    }

//    @Override
//    public Product getProductById(UUID id) {
//        return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
//    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {

        Product product = new Product(productRequest);

        System.out.println("product = " + product);

        //TODO set brand

        ResponseEntity<?> brandResponse = brandAPIClient.getIdByName(productRequest.getBrandName());
        UUID brandId;
        System.out.println("brandResponse = " + brandResponse.getStatusCode());
        System.out.println("brandResponse = " + brandResponse.getBody());
        if (brandResponse.getStatusCode() == HttpStatus.OK) {
            String brandString = (String) brandResponse.getBody();
            assert brandString != null;
            brandId = UUID.fromString(brandString);
            System.out.println("brandId = " + brandId);
            product.setBrandId(brandId);
        }

        //TODO set category

//        ResponseEntity<?> categoryResponse = brandAPIClient.getIdByName(productRequest.getBrandName());
//        UUID categoryId;
//        if (categoryResponse.getStatusCode() == HttpStatus.OK) {
//            categoryId = (UUID) categoryResponse.getBody();
//            product.setCategoryId(categoryId);
//        }

        //TODO set prices

        Product saved = productRepository.save(product);
        System.out.println("saved = " + saved);
        return new ProductResponse(saved);
    }

    @Override
    public ProductResponse update(UUID id, ProductRequest productRequest) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        product.setModel(productRequest.getModel());
        product.setDescription(productRequest.getDescription());
        return new ProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> getAllByFilter(String filter) {
        List<Product> products = new ArrayList<>();
//        products.addAll(productRepository.findAllByBrandName(filter));
        products.addAll(productRepository.findAllByModelContainingIgnoreCase(filter));
        products.addAll(productRepository.findAllByDescriptionContainingIgnoreCase(filter));

        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllByFilterAndStock(String filter) {

        return this.getAllByFilter(filter).stream().filter(productResponse -> productResponse.getStock() > 0).collect(Collectors.toList());
    }
}
