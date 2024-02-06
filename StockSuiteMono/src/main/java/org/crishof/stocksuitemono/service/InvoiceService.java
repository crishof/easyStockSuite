package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.InvoiceRequest;
import org.crishof.stocksuitemono.dto.InvoiceResponse;
import org.crishof.stocksuitemono.model.Invoice;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    List<InvoiceResponse> getAll();

    Invoice getById(UUID id);

    void save(InvoiceRequest invoiceRequest);

    InvoiceResponse update(UUID id, InvoiceRequest invoiceRequest);

    void deleteById(UUID id);
}
