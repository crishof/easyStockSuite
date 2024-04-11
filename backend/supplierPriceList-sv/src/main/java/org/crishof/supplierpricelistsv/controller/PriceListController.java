package org.crishof.supplierpricelistsv.controller;

import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ImportFileException;
import org.crishof.supplierpricelistsv.model.Product;
import org.crishof.supplierpricelistsv.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        System.out.println("supplierId = " + supplierId);
        System.out.println("updateExistingProducts = " + updateExistingProducts);

        int importedCount = 0;
        int alreadyImportedCount = 0;

        try {

            List<Product> products = productService.readExcel(file);

            for (Product product : products) {
                // Verificar si existe un producto con la misma marca y código para este proveedor
                Product existingProduct = productService.findProductByBrandAndCodeAndSupplierId(product.getBrand(), product.getCode(), supplierId);

                if (existingProduct != null) {
                    // Actualizar los precios y stock del producto existente y fecha de actualizacion
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setTaxRate(product.getTaxRate());
                    existingProduct.setSuggestedPrice(product.getSuggestedPrice());
                    existingProduct.setSuggestedWebPrice(product.getSuggestedWebPrice());
                    existingProduct.setStockAvailable(product.getStockAvailable());
                    existingProduct.setCurrency(product.getCurrency());
                    existingProduct.setLastUpdate(LocalDate.now());

                    productService.save(existingProduct);
                    alreadyImportedCount++;
                } else {
                    // Asignar el ID del proveedor al producto y guardarlo
                    product.setSupplierId(supplierId);
                    productService.save(product);
                    importedCount++;
                }
            }

            String message = "Products updated successfully";
            if (importedCount > 0) {
                message += " " + importedCount + " products imported";
            }
            if (alreadyImportedCount > 0) {
                message += " " + alreadyImportedCount + " existing products were updated";
            }

            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (ImportFileException e) {
            return ResponseEntity.internalServerError().body("Error during import: " + e.getMessage());
        }
    }

    @GetMapping("/getAllByFilter")
    public List<ProductResponse> getAllByFilter(@RequestParam(required = false) UUID supplierId, @RequestParam(required = false) String brand, @RequestParam(required = false) String filter) {

        System.out.println("supplierId = " + supplierId);
        System.out.println("brand = " + brand);
        System.out.println("filter = " + filter);

        List<ProductResponse> productResponses = productService.getAllByFilter(supplierId, brand, filter);

        System.out.println("Resultado: " + productResponses.size());

        return productResponses;
    }
}
