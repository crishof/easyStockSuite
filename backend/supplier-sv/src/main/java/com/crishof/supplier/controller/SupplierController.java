package com.crishof.supplier.controller;

import com.crishof.supplier.dto.SupplierRequest;
import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.exception.DuplicateNameException;
import com.crishof.supplier.exception.SupplierNotFoundException;
import com.crishof.supplier.model.Supplier;
import com.crishof.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
//@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/getIdByName")
    public ResponseEntity<?> getIdByName(@RequestParam String supplierName) {
        try {
            SupplierResponse supplierResponse = supplierService.getByName(supplierName);
            return ResponseEntity.ok(supplierResponse.getId());
        } catch (SupplierNotFoundException e) {
            SupplierRequest supplierRequest = new SupplierRequest(supplierName);
            SupplierResponse supplierResponse = supplierService.save(supplierRequest);
            return ResponseEntity.ok(supplierResponse.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody SupplierRequest categoryRequest) {

        try {
            SupplierResponse categoryResponse = supplierService.save(categoryRequest);
            return ResponseEntity.ok(categoryResponse);
        } catch (DuplicateNameException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
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
