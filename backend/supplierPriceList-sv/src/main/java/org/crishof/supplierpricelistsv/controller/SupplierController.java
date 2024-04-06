package org.crishof.supplierpricelistsv.controller;

import org.crishof.supplierpricelistsv.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @GetMapping("/getBrandsBySupplier")
    public List<String> getBrandsBySupplier(UUID supplierId){

        return supplierService.getBrandsBySupplier(supplierId);
    }

    @GetMapping("/getAllBrands")
    public List<String> getAllBrands(){
        return supplierService.getAllBrands();
    }
}
