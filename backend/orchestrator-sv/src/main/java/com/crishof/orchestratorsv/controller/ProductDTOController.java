package com.crishof.orchestratorsv.controller;

import com.crishof.orchestratorsv.dto.ProductDTO;
import com.crishof.orchestratorsv.repository.BrandAPIClient;
import com.crishof.orchestratorsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productDto")
public class ProductDTOController {

    @Autowired
    ProductService productService;

    @Autowired
    BrandAPIClient brandAPIClient;

    @GetMapping("/test")
    public String testURL() {
        return "OK!";
    }

    @GetMapping("/getAllProducts")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/save")
    public String saveProduct(@RequestBody ProductDTO product) {

        productService.saveProduct(product);

        return "";
    }
}
