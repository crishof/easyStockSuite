package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.model.Invoice;

import java.util.List;

public interface InvoiceService {

    List<Invoice> getAll();

    Invoice getById(Long id);

    void save(InvoiceRequest invoiceRequest);

    InvoiceResponse update(Long id, InvoiceRequest invoiceRequest);

    void deleteById(Long id);
}
