package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.model.Supplier;
import org.crishof.stocksuitemono.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {


    @Autowired
    SupplierService supplierService;

    @GetMapping("/findAll")
    public List<Supplier> findAll() {
        return supplierService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Supplier findById(@PathVariable("id") Long id) {
        return supplierService.findById(id);
    }

    @PostMapping("/save")
    public String save(@RequestBody Supplier supplier) {
        supplierService.save(supplier);

        return "Supplier successfully saved";
    }


    @PutMapping("/edit/{id}")
    public Supplier editSupplier(@RequestParam("id") Long id, @RequestBody Supplier supplier) {
        supplierService.update(id, supplier);
        return supplierService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        supplierService.deleteById(id);
        return "Supplier successfully deleted";
    }
}

