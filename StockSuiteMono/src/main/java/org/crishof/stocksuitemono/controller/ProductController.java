package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.dto.ProductRequest;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.service.BrandService;
import org.crishof.stocksuitemono.service.ImportFileService;
import org.crishof.stocksuitemono.service.ProductService;
import org.crishof.stocksuitemono.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productService.findtById(id);
    }

    @PostMapping("/save")
    public String save(@RequestBody ProductRequest productRequest) {

        productService.save(productRequest);

        return "Product successfully saved";
    }

    @PutMapping("/edit/{id}")
    public Product editProduct(@RequestParam("id") Long id, @RequestBody Product product) {
        productService.update(id, product);
        return productService.findtById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "Product successfully deleted";
    }
}
