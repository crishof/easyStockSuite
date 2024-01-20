package com.crishof.productsv.controller;

import com.crishof.productsv.dto.ProductDTO;
import com.crishof.productsv.repository.BrandAPIClient;
import com.crishof.productsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productDto")
public class ProductDTOController {

    @Autowired
    ProductService productService;

    @Autowired
    BrandAPIClient brandAPIClient;

    @PostMapping("/save")
    public String saveProduct(@RequestBody ProductDTO productDTO) {

        brandAPIClient.saveBrandName(productDTO.getBrand());
        Long brandId = brandAPIClient.getBrandIdByName(productDTO.getBrand());
        productService.save(productDTO.getCode(), productDTO.getModel(), productDTO.getDescription(), brandId);

        return "Product saved successfully";
    }
}
