package com.crishof.supplier.controller;

import com.crishof.supplier.model.Supplier;
import com.crishof.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping("/save")
    public String saveSupplier(@RequestBody Supplier supplier) {
        supplierService.save(supplier);

        return "Supplier successfully saved";
    }

    @GetMapping("/getAll")
    public List<Supplier> getAll() {
        return supplierService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Supplier getById(@PathVariable("id") Long id) {
        return supplierService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public Supplier editSupplier(@PathVariable("id") Long id, @RequestBody Supplier supplier) {
        supplierService.update(id, supplier);

        return supplierService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        supplierService.deleteById(id);

        return "Supplier successfully deleted";
    }
}
