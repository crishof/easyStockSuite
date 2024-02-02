package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> findAll();

    Invoice findtById(Long id);

    void save(Invoice invoice);

    Invoice update(Long id, Invoice invoice);

    void deleteById(Long id);
}
