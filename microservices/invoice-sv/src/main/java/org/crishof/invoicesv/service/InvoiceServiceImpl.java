package org.crishof.invoicesv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crishof.invoicesv.apiclient.CustomerApiClient;
import org.crishof.invoicesv.apiclient.ProductAPIClient;
import org.crishof.invoicesv.dto.*;
import org.crishof.invoicesv.exception.InvoiceNotFoundException;
import org.crishof.invoicesv.model.Invoice;
import org.crishof.invoicesv.model.InvoiceItem;
import org.crishof.invoicesv.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service("invoiceService")
@Slf4j
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductAPIClient productAPIClient;
    private final CustomerApiClient customerApiClient;

    @Override
    public List<InvoiceResponse> getAll() {
        return invoiceRepository.findAll().stream().map(this::toInvoiceResponse).toList();
    }

    @Override
    public InvoiceResponse getById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(InvoiceNotFoundException::new);
        return this.toInvoiceResponse(invoice);
    }

    @Override
    public InvoiceResponse save(InvoiceRequest invoiceRequest) {

        Invoice invoice = this.toInvoice(invoiceRequest);

        if (invoice.getCustomerId() == null) {
            UUID customerId = Objects.requireNonNull(customerApiClient.save(invoiceRequest.getCustomerRequest()).getBody()).getId();
            invoice.setCustomerId(customerId);
        }

        invoice.getInvoiceItems().forEach(item -> item.setInvoice(invoice));

        productAPIClient.updateProductStock(this.toInvoiceUpdate(invoiceRequest));

        return this.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    private InvoiceUpdateRequest toInvoiceUpdate(InvoiceRequest invoiceRequest) {
        return InvoiceUpdateRequest.builder()
                .branchId(invoiceRequest.getBranchId())
                .locationId(invoiceRequest.getLocationId())
                .invoiceItems(invoiceRequest.getInvoiceItemsRequest()
                        .stream()
                        .map(this::toInvoiceItem)
                        .toList())
                .build();
    }

    private InvoiceItem toInvoiceItem(InvoiceItemRequest invoiceItemRequest) {

        return InvoiceItem.builder()
                .productId(invoiceItemRequest.getId())
                .price(invoiceItemRequest.getPrice())
                .quantity(invoiceItemRequest.getQuantity())
                .discountRate(invoiceItemRequest.getDiscountRate())
                .taxRate(invoiceItemRequest.getTaxRate())
                .build();
    }

    private Invoice toInvoice(InvoiceRequest invoiceRequest) {

        return Invoice.builder()

                .customerId(invoiceRequest.getCustomerId())
                .branchId(invoiceRequest.getBranchId())
                .invoiceDate(invoiceRequest.getInvoiceDate())
                .invoiceType(invoiceRequest.getInvoiceType())
                .invoiceNumber(this.formatInvoiceNumber(
                        invoiceRequest.getInvoicePrefix(), invoiceRequest.getInvoiceNumber()
                ))
                .packingListNumber(this.formatInvoiceNumber(
                        invoiceRequest.getPackingListPrefix(), invoiceRequest.getPackingListNumber()
                ))
                .taxSave(invoiceRequest.isTaxSave())

                .invoiceItems(invoiceRequest.getInvoiceItemsRequest().stream()
                        .map(this::toInvoiceItem).toList())

                .observations(invoiceRequest.getObservations())
                .subtotal1(invoiceRequest.getSubtotal1())
                .discount(invoiceRequest.getDiscount())
                .interest(invoiceRequest.getInterest())
                .subtotal2(invoiceRequest.getSubtotal2())

                .netValue0(invoiceRequest.getNetValue0())
                .netValue105(invoiceRequest.getNetValue105())
                .netValue21(invoiceRequest.getNetValue21())
                .netValue27(invoiceRequest.getNetValue27())
                .vat105(invoiceRequest.getVat105())
                .vat21(invoiceRequest.getVat21())
                .vat27(invoiceRequest.getVat27())

                .withholdingVat(invoiceRequest.getWithholdingVat())
                .withholdingSuss(invoiceRequest.getWithholdingSuss())
                .withholdingGrossReceiptsTax(invoiceRequest.getWithholdingGrossReceiptsTax())
                .withholdingIncome(invoiceRequest.getWithholdingIncome())
                .stateTax(invoiceRequest.getStateTax())
                .localTax(invoiceRequest.getLocalTax())

                .rounding(invoiceRequest.getRounding())
                .totalPrice(invoiceRequest.getTotalPrice())

                .build();
    }

    private String formatInvoiceNumber(String prefix, String number) {
        String formatedPrefix = String.format("%03d", Integer.parseInt(prefix));
        String formatedNumber = String.format("%03d", Integer.parseInt(number));
        return formatedPrefix + " - " + formatedNumber;
    }

    private InvoiceResponse toInvoiceResponse(Invoice invoice) {

        return InvoiceResponse.builder()
                .customerResponse(customerApiClient.getById(
                        invoice.getCustomerId()).getBody())
                .invoiceDate(invoice.getInvoiceDate())
                .invoiceDate(invoice.getInvoiceDate())
                .invoiceType(invoice.getInvoiceType())
                .invoiceNumber(invoice.getInvoiceNumber())
                .packingListNumber(invoice.getPackingListNumber())
                .invoiceItemsResponse(invoice.getInvoiceItems().stream().map(
                        this::toInvoiceItemResponse).toList())
                .taxSave(invoice.isTaxSave())
                .observations(invoice.getObservations())


                .subtotal1(invoice.getSubtotal1())
                .discount(invoice.getDiscount())
                .interest(invoice.getInterest())
                .subtotal2(invoice.getSubtotal2())

                .netValue21(invoice.getNetValue21())
                .vat21(invoice.getVat21())
                .netValue105(invoice.getNetValue105())
                .vat105(invoice.getVat105())
                .netValue27(invoice.getNetValue27())
                .vat27(invoice.getVat27())
                .netValue0(invoice.getNetValue0())

                .withholdingVat(invoice.getWithholdingVat())
                .withholdingSuss(invoice.getWithholdingSuss())
                .withholdingGrossReceiptsTax(invoice.getWithholdingGrossReceiptsTax())
                .withholdingIncome(invoice.getWithholdingIncome())
                .stateTax(invoice.getStateTax())
                .localTax(invoice.getLocalTax())

                .rounding(invoice.getRounding())

                .totalPrice(invoice.getTotalPrice())
                .build();
    }

    private InvoiceItemResponse toInvoiceItemResponse(InvoiceItem invoiceItem) {
        return InvoiceItemResponse.builder()
                .productId(invoiceItem.getProductId())
                .price(invoiceItem.getPrice())
                .discountRate(invoiceItem.getDiscountRate())
                .taxRate(invoiceItem.getTaxRate())
                .quantity(invoiceItem.getQuantity())
                .build();
    }
}