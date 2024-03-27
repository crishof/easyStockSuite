package com.crishof.productsv.controller;

import com.crishof.productsv.dto.ProductRequest;
import com.crishof.productsv.dto.ProductResponse;
import com.crishof.productsv.exeption.ImportFileException;
import com.crishof.productsv.exeption.ProductNotFoundException;
import com.crishof.productsv.service.ImportFileService;
import com.crishof.productsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ImportFileService importFileService;

    @Value("${server.port}")
    private int serverPort;

//    @GetMapping("/test")
//    public String LoadBalancerTest() {
//        return "Estoy en el puerto: " + serverPort;
//    }

    @GetMapping("/getAll")
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        try {
            ProductResponse productResponse = productService.getById(id);
            return ResponseEntity.ok(productResponse);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductRequest productRequest) {

        System.out.println("productRequest = " + productRequest);

        try {
            ProductResponse productResponse = productService.save(productRequest);
            return ResponseEntity.ok(productResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PutMapping("/update/{id}")
    public ProductResponse updateProduct(@PathVariable("id") UUID id, @RequestBody ProductRequest productRequest) {
        return productService.update(id, productRequest);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
//        productService.deleteById(id);
//        return ResponseEntity.ok("Product successfully deleted");
//    }

    @GetMapping("/getAllByFilter")
    public List<ProductResponse> getAllByFilter(@RequestParam String filter) {
        return productService.getAllByFilter(filter);
    }

    @GetMapping("/getAllByFilterAndStock")
    public List<ProductResponse> getAllByFilterAndStock(@RequestParam String filter) {
        return productService.getAllByFilterAndStock(filter);
    }

    @PostMapping("/importList")
    public String importFile(@RequestParam MultipartFile file, @RequestParam String supplierName) {

        System.out.println("file = " + supplierName);

        try {

            List<ProductRequest> products = importFileService.readExcel(file);

            for (ProductRequest product : products) {
                product.setSupplierName(supplierName);
                productService.save(product);
            }

            return "All products imported";
        } catch (ImportFileException e) {
            return "Error during import: " + e.getMessage();
        }
    }
}
