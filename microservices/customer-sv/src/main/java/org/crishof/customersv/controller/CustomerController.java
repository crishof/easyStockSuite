package org.crishof.customersv.controller;

import org.crishof.customersv.dto.CustomerRequest;
import org.crishof.customersv.dto.CustomerResponse;
import org.crishof.customersv.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CustomerResponse>> getAll() {

        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/getById/{id}")
    public CustomerResponse getById(@PathVariable("id") UUID id) {
        return customerService.getById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<CustomerResponse> save(@RequestBody CustomerRequest customerRequest) {
        System.out.println("customerRequest = " + customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customerRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.deleteById(customerId));
    }
}
