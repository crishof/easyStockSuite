package com.crishof.orchestratorsv.repository;

import com.crishof.orchestratorsv.model.Brand;
import com.crishof.orchestratorsv.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product-sv")
public interface ProductAPIClient {

    @GetMapping("/product/getAll")
    List<Product> getAllProducts();
    @PostMapping("/product/brand/save")
    String save(Product product);




}
