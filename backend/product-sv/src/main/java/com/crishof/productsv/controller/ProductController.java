package com.crishof.productsv.controller;

import com.crishof.productsv.model.Product;
import com.crishof.productsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/test")
    public String LoadBalancerTest(){
        return "Estoy en el puerto: " + serverPort;
    }

    @PostMapping("/save")
    public String saveProduct(@RequestBody Product product) {
        productService.save(product);
        return "Product saved successfully";
    }

    @GetMapping("/getById/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @PutMapping("/edit/{id}")
    public Product editProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.update(id, product);
        return productService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "Product deleted successfully";
    }
}
