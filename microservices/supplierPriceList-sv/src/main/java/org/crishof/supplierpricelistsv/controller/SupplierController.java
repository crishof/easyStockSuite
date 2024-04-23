package org.crishof.supplierpricelistsv.controller;

import org.crishof.supplierpricelistsv.service.SupplierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    final
    SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/getBrandsBySupplier")
    public List<String> getBrandsBySupplier(@RequestParam UUID supplierId) {

        return supplierService.getBrandsBySupplier(supplierId);
    }

    @GetMapping("/getAllBrands")
    public List<String> getAllBrands() {
        return supplierService.getAllBrands();
    }
}
