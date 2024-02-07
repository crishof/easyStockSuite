package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.dto.ProductResponse;
import org.crishof.stocksuitemono.model.Invoice;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Stock;
import org.crishof.stocksuitemono.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    StockService stockService;

    @Override
    public List<InvoiceResponse> getAll() {

        List<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceResponse> responses = new ArrayList<>();

        for (Invoice i : invoices) {
            InvoiceResponse r = new InvoiceResponse();
            r.setId(i.getId());
            r.setReceptionDate(i.getReceptionDate());
            r.setDueDate(i.getDueDate());
            r.setEntityId(i.getEntityId());
            r.setInvoiceNumber(i.getInvoiceNumber());
            r.setIssueDate(i.getIssueDate());
            r.setTransactionType(i.getTransactionType());
            r.setProductList(new ArrayList<>());

            for (Product product : i.getProductList()) {
                ProductResponse pr = new ProductResponse(product);
                r.getProductList().add(pr);
            }
//            r.setProductList(i.getProductList());
            r.setQuantities(i.getQuantities());
            responses.add(r);
        }

        return responses;
//        return invoiceRepository.findAll().stream().map(InvoiceResponse::new).collect(Collectors.toList());

    }

    @Override
    public Invoice getById(UUID id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    @Override
    public void save(InvoiceRequest invoiceRequest) {

        Invoice invoice = new Invoice(invoiceRequest);
        switch (invoiceRequest.getTransactionType()) {

            case SALE, TRANSFER -> {

                System.out.println("INGRESO A CASO SALE");
                System.out.println("invoiceRequest = " + invoiceRequest.getTransactionType());
                System.out.println("invoice.getTransactionType() = " + invoice.getTransactionType());
                int count = 0;
                for (Product product : invoiceRequest.getProductList()) {
                    Stock stock = stockService.save(product, invoiceRequest.getQuantities().get(count) * -1);
                    product.getStocks().add(stock);
                    count++;
                }
            }
            case PURCHASE, RETURN -> {
                int count = 0;
                for (Product product : invoiceRequest.getProductList()) {
                    Stock stock = stockService.save(product, invoiceRequest.getQuantities().get(count));
                    product.getStocks().add(stock);
                    count++;
                }
            }

            default -> throw new IllegalStateException("Unexpected value: " + invoiceRequest.getTransactionType());
        }
        invoiceRepository.save(invoice);

    }

    @Override
    public InvoiceResponse update(UUID id, InvoiceRequest invoiceRequest) {

        Invoice invoice = this.getById(id);

        invoice.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
        invoice.setDueDate(invoiceRequest.getDueDate());
        invoice.setReceptionDate(invoiceRequest.getReceptionDate());
        invoice.setIssueDate(invoiceRequest.getIssueDate());
        invoice.setProductList(invoiceRequest.getProductList());
        invoice.setEntityId(invoiceRequest.getEntityId());

        return new InvoiceResponse(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteById(UUID id) {
        invoiceRepository.deleteById(id);
    }
}