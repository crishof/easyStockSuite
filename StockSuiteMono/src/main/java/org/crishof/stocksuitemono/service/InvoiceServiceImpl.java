package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.model.Invoice;
import org.crishof.stocksuitemono.model.Product;
import org.crishof.stocksuitemono.model.Stock;
import org.crishof.stocksuitemono.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    StockService stockService;

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getById(UUID id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    @Override
    public void save(InvoiceRequest invoiceRequest) {
        Invoice invoice = new Invoice(invoiceRequest);

        int count = 0;
        for (Product product : invoiceRequest.getProductList()) {
            Stock stock = stockService.save(product, invoiceRequest.getQuantities().get(count));
            product.getStocks().add(stock);
            count++;
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
        invoice.setSupplierId(invoiceRequest.getSupplierId());

        return new InvoiceResponse(invoiceRepository.save(invoice));
    }

    @Override
    public void deleteById(UUID id) {
        invoiceRepository.deleteById(id);
    }
}