package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.dto.CustomerResponse;
import org.crishof.stocksuitemono.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CustomerResponse> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/getById/{id}")
    public CustomerResponse getById(@PathVariable("id")UUID id) {
        return customerService.getById(id);
    }
}
