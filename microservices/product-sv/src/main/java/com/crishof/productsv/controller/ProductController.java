package com.crishof.productsv.controller;

import com.crishof.productsv.dto.InvoiceUpdateRequest;
import com.crishof.productsv.dto.ProductRequest;
import com.crishof.productsv.dto.ProductResponse;
import com.crishof.productsv.dto.SupplierProductRequest;
import com.crishof.productsv.exeption.ProductNotFoundException;
import com.crishof.productsv.repository.ProductRepository;
import com.crishof.productsv.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @Value("${server.port}")
    private int serverPort;

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

    @PutMapping("/invoice")
    public String updateProductStockAndPrices(@RequestBody InvoiceUpdateRequest invoiceUpdateRequest) {


        return productService.updateFromInvoice(invoiceUpdateRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product successfully deleted");
    }

    @GetMapping("/checkProductsByBrand/{brandId}")
    public ResponseEntity<Boolean> checkProductsByBrand(@PathVariable UUID brandId) {
        boolean productsExist = productService.checkProductsByBrand(brandId);
        return ResponseEntity.ok(productsExist);
    }

    @GetMapping("/getAllByFilter")
    public List<ProductResponse> getAllByFilter(@RequestParam String filter, @RequestParam(required = false) UUID supplierId) {

        if (supplierId != null) {
            return productService.getAllByFilterAndSupplier(filter, supplierId);
        }
        return productService.getAllByFilter(filter);
    }

    @GetMapping("/getAllByFilterAndStock")
    public List<ProductResponse> getAllByFilterAndStock(@RequestParam String filter) {
        return productService.getAllByFilterAndStock(filter);
    }

    @PutMapping("/removeCategory")
    public ResponseEntity<String> removeCategory(@RequestParam UUID categoryId) {
        try {
            productService.removeCategory(categoryId);
            return ResponseEntity.ok("Category removed from associated products");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove category from associated products");
        }
    }

    @GetMapping("/countProductsByBrand/{brandId}")
    public Long countProductsByBrand(@PathVariable UUID brandId) {
        return productService.countProductsByBrand(brandId);
    }

    @PostMapping("/importProducts")
    public ResponseEntity<String> importProduct(@RequestBody List<SupplierProductRequest> productList) {

        int importedCount = 0;
        int alreadyImportedCount = 0;

        for (SupplierProductRequest request : productList) {


            if (productRepository.findBySupplierProductId(request.getId()) == null) {
                ProductRequest productRequest = new ProductRequest(request);
                productService.save(productRequest);
                importedCount++;
            } else {
                alreadyImportedCount++;
            }
        }

        String message = "Task completed successfully: ";
        if (productList.isEmpty()) {
            message = "Product list is empty";
        }
        if (importedCount > 0) {
            message += " " + importedCount + " products imported";
        }
        if (alreadyImportedCount > 0) {
            message += " " + alreadyImportedCount + " products already imported";
        }

        return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
    }
}
