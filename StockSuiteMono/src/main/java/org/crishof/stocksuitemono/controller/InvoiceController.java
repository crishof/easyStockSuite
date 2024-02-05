package org.crishof.stocksuitemono.controller;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/save")
    public String saveInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        invoiceService.save(invoiceRequest);

        return "Invoice successfully saved";

    }
}
