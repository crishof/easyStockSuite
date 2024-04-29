package com.crishof.supplierinvoicesv.service;

import com.crishof.supplierinvoicesv.apiClient.ProductAPIClient;
import com.crishof.supplierinvoicesv.dto.*;
import com.crishof.supplierinvoicesv.model.Invoice;
import com.crishof.supplierinvoicesv.model.InvoiceItem;
import com.crishof.supplierinvoicesv.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public InvoiceResponse save(InvoiceRequest invoiceRequest) {

        Invoice invoice = this.toInvoice(invoiceRequest);

        invoice.getInvoiceItems().forEach(item -> item.setInvoice(invoice));

        InvoiceUpdateRequest invoiceUpdateRequest = new InvoiceUpdateRequest();

        invoiceUpdateRequest.setBranchId(invoiceRequest.getBranchId());
        invoiceUpdateRequest.setLocationId(invoiceRequest.getLocationId());
        invoiceUpdateRequest.setInvoiceItems(invoiceRequest.getInvoiceItemsRequest()
                .stream()
                .map(this::toSupplierInvoiceItem)
                .toList());


        productAPIClient.updateProductStockAndPrices(invoiceUpdateRequest);

        return this.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    private SupplierInvoiceItem toSupplierInvoiceItem(InvoiceItemRequest invoiceItem) {

        SupplierInvoiceItem supplierInvoiceItem = new SupplierInvoiceItem();
        supplierInvoiceItem.setId(invoiceItem.getId());
        supplierInvoiceItem.setPrice(invoiceItem.getPrice());
        supplierInvoiceItem.setQuantity(invoiceItem.getQuantity());
        supplierInvoiceItem.setTaxRate(invoiceItem.getTaxRate());
        supplierInvoiceItem.setDiscountRate(invoiceItem.getDiscountRate());
        return supplierInvoiceItem;
    }

    private Invoice toInvoice(InvoiceRequest invoiceRequest) {

        return Invoice.builder()
                .invoiceNumber(invoiceRequest.getInvoiceNumber())
                .invoiceDate(invoiceRequest.getInvoiceDate())
                .dueDate(invoiceRequest.getDueDate())
                .receivedDate(invoiceRequest.getReceivedDate())
                .discount(invoiceRequest.getDiscount())
                .total(invoiceRequest.getTotal())
                .supplierId(invoiceRequest.getSupplierId())
                .invoiceItems(invoiceRequest.getInvoiceItemsRequest().stream()
                        .map(this::toInvoiceItem).toList())
                .build();
    }

    private InvoiceItem toInvoiceItem(InvoiceItemRequest invoiceItemRequest) {

        return InvoiceItem.builder()
                .price(invoiceItemRequest.getPrice())
                .quantity(invoiceItemRequest.getQuantity())
                .discountRate(invoiceItemRequest.getDiscountRate())
                .taxRate(invoiceItemRequest.getTaxRate())
                .build();
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
        invoiceResponse.setInvoiceItems(invoice.getInvoiceItems());

        return invoiceResponse;

    }
}
