package org.crishof.supplierpricelistsv.controller;

import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ImportFileException;
import org.crishof.supplierpricelistsv.exception.ProductNotFoundException;
import org.crishof.supplierpricelistsv.model.Product;
import org.crishof.supplierpricelistsv.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/priceList")
public class PriceListController {

    final
    ProductService productService;

    public PriceListController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/importList")
    public ResponseEntity<String> importFile(@RequestParam MultipartFile file, @RequestParam UUID supplierId, @RequestParam boolean updateExistingProducts) {

        int importedCount = 0;
        int alreadyImportedCount = 0;

        try {

            List<Product> products = productService.readExcel(file);

            for (Product product : products) {

                Product existingProduct = productService.findProductByBrandAndCodeAndSupplierId(product.getBrand(), product.getCode(), supplierId);

                if (existingProduct != null && updateExistingProducts) {

                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setTaxRate(product.getTaxRate());
                    existingProduct.setSuggestedPrice(product.getSuggestedPrice());
                    existingProduct.setSuggestedWebPrice(product.getSuggestedWebPrice());
                    existingProduct.setStockAvailable(product.getStockAvailable());
                    existingProduct.setCurrency(product.getCurrency());
                    existingProduct.setLastUpdate(LocalDate.now());

                    productService.save(existingProduct);
                    alreadyImportedCount++;
                } else if (existingProduct == null) {
                    product.setSupplierId(supplierId);
                    productService.save(product);
                    importedCount++;
                }
            }

            String message = "Task completed successfully";
            if (importedCount > 0) {
                message += " " + importedCount + " products imported";
            }
            if (alreadyImportedCount > 0) {
                message += " " + alreadyImportedCount + " existing products were updated";
            }
            if (importedCount == 0 && alreadyImportedCount == 0) {
                message += " " + " no products were updated";
            }

            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (ImportFileException | IOException e) {
            return ResponseEntity.internalServerError().body("Error during import: " + e.getMessage());
        }
    }

    @GetMapping("/getAllByFilter")
    public List<ProductResponse> getAllByFilter(@RequestParam(required = false) UUID supplierId, @RequestParam(required = false) String brand, @RequestParam(required = false) String filter) {

        return productService.getAllByFilter(supplierId, brand, filter);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable(name = "id") UUID id) throws ProductNotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getById(id));
        } catch (ProductNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
