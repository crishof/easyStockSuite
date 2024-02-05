package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.model.Invoice;
import org.crishof.stocksuitemono.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    @Override
    public void save(InvoiceRequest invoiceRequest) {
        Invoice invoice = new Invoice(invoiceRequest);
        invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceResponse update(Long id, InvoiceRequest invoiceRequest) {

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
    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }
}