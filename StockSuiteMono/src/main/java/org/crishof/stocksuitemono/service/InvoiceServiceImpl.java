package org.crishof.stocksuitemono.service;

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
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findtById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }


    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public Invoice update(Long id, Invoice invoice) {

        Invoice invoice1 = this.findtById(id);

        invoice1.setInvoiceNumber(invoice.getInvoiceNumber());
        invoice1.setDueDate(invoice.getDueDate());
        invoice1.setReceptionDate(invoice.getReceptionDate());
        invoice1.setIssueDate(invoice.getIssueDate());
        invoice1.setProductList(invoice.getProductList());
        invoice1.setSupplierId(invoice.getSupplierId());

        return invoiceRepository.save(invoice1);
    }

    @Override
    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }
}