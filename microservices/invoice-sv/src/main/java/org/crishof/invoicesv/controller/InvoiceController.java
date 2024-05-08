package org.crishof.invoicesv.controller;

import lombok.RequiredArgsConstructor;
import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.dto.InvoiceResponse;
import org.crishof.invoicesv.exception.InvoiceNotFoundException;
import org.crishof.invoicesv.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/getAll")
    public List<InvoiceResponse> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {

        try {
            InvoiceResponse invoiceResponse = invoiceService.getById(id);
            return ResponseEntity.ok(invoiceResponse);
        } catch (InvoiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/save/purchase")
    public ResponseEntity<?> savePurchaseInvoice(@RequestBody InvoiceRequest invoiceRequest) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body("Invoice successfully saved");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving invoice: " + e.getMessage());
        }
    }

//    @PostMapping("/save/sale")
//    public String saveSaleInvoice(@RequestBody InvoiceRequest invoiceRequest, @RequestParam String customerName, @RequestParam String customerLastName, @RequestParam String customerDni) {
//        try {
//            Customer customer = customerService.save(customerName, customerLastName, customerDni);
//            invoiceRequest.setEntityId(customer.getId());
//            invoiceService.save(invoiceRequest);
//            return "Invoice successfully saved";
//        } catch (Exception e) {
//            return "Error saving invoice: " + e.getMessage();
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") UUID id) {
        invoiceService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Invoice successfully deleted");
    }
}

