package org.crishof.stocksuitemono.controller;

import jakarta.persistence.EntityNotFoundException;
import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.dto.ProductResponse;
import org.crishof.stocksuitemono.exception.IOException.ImportFileException;
import org.crishof.stocksuitemono.service.BrandService;
import org.crishof.stocksuitemono.service.ImportFileService;
import org.crishof.stocksuitemono.service.ProductService;
import org.crishof.stocksuitemono.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;
    @Autowired
    ImportFileService importFileService;
    @Autowired
    SupplierService supplierService;

    @PostMapping("/importList")
    public String importFile(@RequestParam MultipartFile file, @RequestParam String supplierName) {

        try {

            List<ProductRequest> products = importFileService.readExcel(file);

            for (ProductRequest product : products) {
                product.setSupplier(supplierName);
                productService.save(product);
            }

            return "All products imported";
        } catch (ImportFileException e) {
            return "Error during import: " + e.getMessage();
        }
    }

    @GetMapping("/getAll")
    public List<ProductResponse> getAll() {
        return productService.getAll();
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable("id") UUID id) {
        try {
            ProductResponse productResponse = productService.getById(id);
            return ResponseEntity.ok(productResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public String save(@RequestBody ProductRequest productRequest) {

        productService.save(productRequest);

        return "Product successfully saved";
    }

    @PutMapping("/update/{id}")
    public ProductResponse updateProduct(@PathVariable("id") UUID id, @RequestBody ProductRequest productRequest) {
        return productService.update(id, productRequest);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        productService.deleteById(id);
        return "Product successfully deleted";
    }
}
