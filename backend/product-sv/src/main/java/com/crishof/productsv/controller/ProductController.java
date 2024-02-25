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

    //    1 - Crear nuevo producto
    @PostMapping("/save")
    public String saveProduct(@RequestBody Product product) {
        productService.save(product);
        return "Product saved successfully";
    }

    //    2 - Obtener producto
    @GetMapping("/getById/{id}")
    public Product getById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    //    3 - Obtener todos los productos
    @GetMapping("/getAll")
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    //    4 - Editar un producto
    @PutMapping("/edit/{id}")
    public Product editProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        productService.update(id, product);
        return productService.findById(id);
    }
//    5 - Eliminar un producto

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "Product deleted successfully";
    }
}