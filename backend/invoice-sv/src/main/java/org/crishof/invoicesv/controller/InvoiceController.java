package org.crishof.invoicesv.controller;

import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@CrossOrigin(origins = "http://localhost:4200")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    CustomerService customerService;

    @PostMapping("/save/purchase")
    public String savePurchaseInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        try {

            invoiceService.save(invoiceRequest);
            return "Invoice successfully saved";

        } catch (Exception e) {
            return "Error saving invoice: " + e.getMessage();
        }
    }

    @PostMapping("/save/sale")
    public String saveSaleInvoice(@RequestBody InvoiceRequest invoiceRequest, @RequestParam String customerName, @RequestParam String customerLastName, @RequestParam String customerDni) {

        try {

            Customer customer = customerService.save(customerName, customerLastName, customerDni);

            invoiceRequest.setEntityId(customer.getId());

            invoiceService.save(invoiceRequest);
            return "Invoice successfully saved";

        } catch (Exception e) {
            return "Error saving invoice: " + e.getMessage();
        }
    }

    @GetMapping("/getAll")
    public List<InvoiceResponse> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/getById/{id}")
    public InvoiceResponse getById(@PathVariable("id") UUID id) {
        return invoiceService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") UUID id) {
        invoiceService.deleteById(id);
        return "Invoice successfully deleted";
    }
}

