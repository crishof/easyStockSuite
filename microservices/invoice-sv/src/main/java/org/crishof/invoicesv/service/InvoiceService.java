package org.crishof.invoicesv.service;

import org.crishof.invoicesv.dto.InvoiceRequest;
import org.crishof.invoicesv.dto.InvoiceResponse;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    List<InvoiceResponse> getAll();

    InvoiceResponse getById(UUID id);

    InvoiceResponse save(InvoiceRequest invoiceRequest);
}
