package com.crishof.supplierinvoicesv.service;

import com.crishof.supplierinvoicesv.dto.InvoiceRequest;
import com.crishof.supplierinvoicesv.dto.InvoiceResponse;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    List<InvoiceResponse> getAll();

    List<InvoiceResponse> getAllBySupplierId(UUID supplierId);

    InvoiceResponse getById(UUID id);

    InvoiceResponse save(InvoiceRequest invoiceRequest);

    void deleteById(UUID id);
}
