package com.crishof.supplierinvoicesv.service;

import com.crishof.supplierinvoicesv.apiClient.ProductAPIClient;
import com.crishof.supplierinvoicesv.dto.InvoiceRequest;
import com.crishof.supplierinvoicesv.dto.InvoiceResponse;
import com.crishof.supplierinvoicesv.model.Invoice;
import com.crishof.supplierinvoicesv.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductAPIClient productAPIClient;

    @Override
    public List<InvoiceResponse> getAll() {
        return invoiceRepository.findAll().stream()
                .map(this::toInvoiceResponse).toList();
    }

    @Override
    public InvoiceResponse getById(UUID id) {
        return this.toInvoiceResponse(invoiceRepository.getReferenceById(id));
    }

    @Override
    public InvoiceResponse save(InvoiceRequest invoiceRequest) {

        Invoice invoice = this.toInvoice(invoiceRequest);

        ResponseEntity.ok(productAPIClient.updateProductStockAndPrices(invoiceRequest.getProductRequests()));

        return this.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    private Invoice toInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
        invoice.setInvoiceDate(invoiceRequest.getInvoiceDate());
        invoice.setDueDate(invoiceRequest.getDueDate());
        invoice.setReceivedDate(invoiceRequest.getReceivedDate());
        invoice.setDiscount(invoiceRequest.getDiscount());
        invoice.setTotal(invoiceRequest.getTotal());
        invoice.setSupplierId(invoiceRequest.getSupplierId());
        invoice.setProductRequests(invoiceRequest.getProductRequests());

        return invoice;
    }

    private InvoiceResponse toInvoiceResponse(Invoice invoice) {
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setId(invoice.getId());
        invoiceResponse.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceResponse.setInvoiceDate(invoice.getInvoiceDate());
        invoiceResponse.setDueDate(invoice.getDueDate());
        invoiceResponse.setReceivedDate(invoice.getReceivedDate());
        invoiceResponse.setDiscount(invoice.getDiscount());
        invoiceResponse.setTotal(invoice.getTotal());
        invoiceResponse.setSupplierId(invoice.getSupplierId());
        invoiceResponse.setProductRequests(invoice.getProductRequests());

        return invoiceResponse;

    }
}
