package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.model.Invoice;
import org.crishof.stocksuitemono.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")

public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/save")
    public String saveInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        try {

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

