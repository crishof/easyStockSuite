package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.model.Supplier;
import org.crishof.stocksuitemono.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
public class SupplierController {


    @Autowired
    SupplierService supplierService;

    @GetMapping("/getAll")
    public List<Supplier> getAll() {
        return supplierService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Supplier getById(@PathVariable("id") UUID id) {
        return supplierService.getById(id);
    }

    @PostMapping("/save")
    public String save(@RequestBody Supplier supplier) {
        supplierService.save(supplier);

        return "Supplier successfully saved";
    }


    @PutMapping("/update/{id}")
    public Supplier updateSupplier(@RequestParam("id") UUID id, @RequestBody Supplier supplier) {
        return supplierService.update(id, supplier);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        supplierService.deleteById(id);
        return "Supplier successfully deleted";
    }
}

