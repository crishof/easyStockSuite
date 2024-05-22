package com.crishof.supplier.controller;

import com.crishof.supplier.dto.SupplierRequest;
import com.crishof.supplier.dto.SupplierResponse;
import com.crishof.supplier.exception.DuplicateNameException;
import com.crishof.supplier.exception.SupplierNotFoundException;
import com.crishof.supplier.model.Supplier;
import com.crishof.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private static final String SERVER_ERROR = "Internal Server Error";
    private final SupplierService supplierService;

    @GetMapping("/getAll")
    public List<SupplierResponse> getAll() {
        return supplierService.getAll();
    }

    @GetMapping("/getAllByFilter")
    List<SupplierResponse> getAllByFilter(@RequestParam String filter) {
        return supplierService.getAllByFilter(filter);
    }

    @GetMapping("/getById/{id}")
    public SupplierResponse getById(@PathVariable("id") UUID id) {
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
            return ResponseEntity.status(HttpStatus.OK).body(supplierResponse.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVER_ERROR);
        }
    }


    @PutMapping("/update/{id}")
    public SupplierResponse updateSupplier(@PathVariable("id") UUID id, @RequestBody SupplierRequest supplier) {
        return supplierService.update(id, supplier);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        supplierService.deleteById(id);
        return "Supplier successfully deleted";
    }
}
