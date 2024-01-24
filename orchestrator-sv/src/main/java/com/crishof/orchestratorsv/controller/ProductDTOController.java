package com.crishof.orchestratorsv.controller;

import com.crishof.orchestratorsv.repository.BrandAPIClient;
import com.crishof.orchestratorsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productDto")
public class ProductDTOController {

    @Autowired
    ProductService productService;

    @Autowired
    BrandAPIClient brandAPIClient;
}
