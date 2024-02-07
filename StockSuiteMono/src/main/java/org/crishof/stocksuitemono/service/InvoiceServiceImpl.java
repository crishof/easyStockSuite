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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    StockService stockService;

    @Override
    public List<InvoiceResponse> getAll() {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream().map(i -> {
            InvoiceResponse r = new InvoiceResponse();
            r.setId(i.getId());
            r.setReceptionDate(i.getReceptionDate());
            r.setDueDate(i.getDueDate());
            r.setEntityId(i.getEntityId());
            r.setInvoiceNumber(i.getInvoiceNumber());
            r.setIssueDate(i.getIssueDate());
            r.setTransactionType(i.getTransactionType());
            r.setProductList(i.getProductList().stream().map(ProductResponse::new).collect(Collectors.toList()));
            r.setQuantities(i.getQuantities());
            return r;
        }).collect(Collectors.toList());
    }

    @Override
    public InvoiceResponse getById(UUID id) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);

        return invoiceOptional.map(InvoiceResponse::new).orElse(null);
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

        Invoice invoice = invoiceRepository.getReferenceById(id);

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