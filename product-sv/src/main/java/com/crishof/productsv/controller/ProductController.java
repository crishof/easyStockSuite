package com.crishof.productsv.controller;

import com.crishof.productsv.model.Product;
import com.crishof.productsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PostMapping("/save")
    public String saveProduct(@RequestBody Product product) {

        productService.save(product);

        return "Product saved successfully";
    }
}
