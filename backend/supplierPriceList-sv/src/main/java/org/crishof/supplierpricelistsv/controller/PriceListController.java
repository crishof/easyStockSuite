package org.crishof.supplierpricelistsv.controller;

import org.crishof.supplierpricelistsv.dto.ProductResponse;
import org.crishof.supplierpricelistsv.exception.ImportFileException;
import org.crishof.supplierpricelistsv.model.Product;
import org.crishof.supplierpricelistsv.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/priceList")
public class PriceListController {

    @Autowired
    ProductService productService;

    @PostMapping("/importList")
    public String importFile(@RequestParam MultipartFile file, @RequestParam UUID supplierId) {

        try {

            List<Product> products = productService.readExcel(file);

            for (Product product : products) {
                // Verificar si existe un producto con la misma marca y código para este proveedor
                Product existingProduct = productService.findProductByBrandAndCodeAndSupplierId(product.getBrand(), product.getCode(), supplierId);

                if (existingProduct != null) {
                    // Actualizar los precios del producto existente
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setTaxRate(product.getTaxRate());
                    existingProduct.setSuggestedPrice(product.getSuggestedPrice());
                    existingProduct.setSuggestedWebPrice(product.getSuggestedWebPrice());

                    productService.save(existingProduct);
                } else {
                    // Asignar el ID del proveedor al producto y guardarlo
                    product.setSupplierId(supplierId);
                    productService.save(product);
                }
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
}
