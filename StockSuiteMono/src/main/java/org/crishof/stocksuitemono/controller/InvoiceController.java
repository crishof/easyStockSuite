package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.model.Customer;
import org.crishof.stocksuitemono.service.CustomerService;
import org.crishof.stocksuitemono.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")

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
    public String saveSaleInvoice(@RequestBody InvoiceRequest invoiceRequest,
                                  @RequestParam String customerName,@RequestParam String customerLastName,
                                  @RequestParam String customerDni) {

        try {

            Customer customer = customerService.save(customerName,customerLastName,customerDni);

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
}

