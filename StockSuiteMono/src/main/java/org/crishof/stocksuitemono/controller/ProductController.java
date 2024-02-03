package org.crishof.stocksuitemono.controller;

import jakarta.persistence.EntityNotFoundException;
import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.dto.ProductResponse;
import org.crishof.stocksuitemono.service.BrandService;
import org.crishof.stocksuitemono.service.ImportFileService;
import org.crishof.stocksuitemono.service.ProductService;
import org.crishof.stocksuitemono.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String importFile(@RequestParam String filePath, @RequestParam String supplierName) {

        List<ProductRequest> products = importFileService.readExcel(filePath);

        for (ProductRequest product : products) {

            product.setSupplier(supplierName);

            productService.save(product);
        }

        return "All products imported";
    }

    @GetMapping("/findAll")
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }


    @GetMapping("/findById/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("id") Long id) {
        try {
            ProductResponse productResponse = productService.findById(id);
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

    @PutMapping("/edit/{id}")
    public ProductResponse editProduct(@RequestParam("id") Long id, @RequestBody ProductRequest productRequest) {
        productService.update(id, productRequest);
        return productService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "Product successfully deleted";
    }
}
