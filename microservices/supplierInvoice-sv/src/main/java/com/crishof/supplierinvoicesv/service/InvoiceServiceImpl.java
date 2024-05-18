package com.crishof.supplierinvoicesv.service;

import com.crishof.supplierinvoicesv.apiClient.ProductAPIClient;
import com.crishof.supplierinvoicesv.dto.*;
import com.crishof.supplierinvoicesv.exception.InvoiceNotFoundException;
import com.crishof.supplierinvoicesv.model.Invoice;
import com.crishof.supplierinvoicesv.model.InvoiceItem;
import com.crishof.supplierinvoicesv.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
    public List<InvoiceResponse> getAllBySupplierId(UUID supplierId) {
        return invoiceRepository.findAllBySupplierId(supplierId).stream()
                .map(this::toInvoiceResponse).toList();
    }

    @Override
    public InvoiceResponse getById(UUID id) {

        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            return this.toInvoiceResponse(invoice.get());
        }
        throw new InvoiceNotFoundException(id);
    }

    @Override
    @Transactional
    public InvoiceResponse save(InvoiceRequest invoiceRequest) {

        Invoice invoice = this.toInvoice(invoiceRequest);

        invoice.getInvoiceItems().forEach(item -> item.setInvoice(invoice));

        productAPIClient.updateProductStockAndPrices(
                this.toInvoiceUpdate(invoiceRequest));

        return this.toInvoiceResponse(invoiceRepository.save(invoice));

    }

    @Override
    public void deleteById(UUID id) {
        // TODO implement rollback
    }

    private String formatInvoiceNumber(String prefix, String number) {
        String formatedPrefix = String.format("%03d", Integer.parseInt(prefix));
        String formatedNumber = String.format("%03d", Integer.parseInt(number));
        return formatedPrefix + " - " + formatedNumber;
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

    private Invoice toInvoice(InvoiceRequest invoiceRequest) {

        return Invoice.builder()
                .supplierId(invoiceRequest.getSupplierId())

                .location(invoiceRequest.getLocation())

                .branchId(invoiceRequest.getBranchId())
                .locationId(invoiceRequest.getLocationId())

                .invoiceDate(invoiceRequest.getInvoiceDate())
                .dueDate(invoiceRequest.getDueDate())
                .receptionDate(invoiceRequest.getReceptionDate())
                .savedDate(invoiceRequest.getSavedDate())

                .invoiceType(invoiceRequest.getInvoiceType())
                .invoiceNumber(this.formatInvoiceNumber(
                        invoiceRequest.getInvoicePrefix(), invoiceRequest.getInvoiceNumber()
                ))
                .packingListNumber(this.formatInvoiceNumber(
                        invoiceRequest.getPackingListPrefix(), invoiceRequest.getPackingListNumber()
                ))

                .invoiceItems(invoiceRequest.getInvoiceItemsRequest().stream()
                        .map(this::toInvoiceItem).toList())

                .fixedAsset(invoiceRequest.isFixedAsset())
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

                .internalTax(invoiceRequest.getInternalTax())

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

    private InvoiceItem toInvoiceItem(InvoiceItemRequest invoiceItemRequest) {

        return InvoiceItem.builder()
                .productId(invoiceItemRequest.getId())
                .price(invoiceItemRequest.getPrice())
                .quantity(invoiceItemRequest.getQuantity())
                .discountRate(invoiceItemRequest.getDiscountRate())
                .taxRate(invoiceItemRequest.getTaxRate())
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


    private InvoiceResponse toInvoiceResponse(Invoice invoice) {
        return InvoiceResponse.builder()

                .supplierId(invoice.getSupplierId())

                .branchId(invoice.getBranchId())
                .locationId(invoice.getLocationId())

                .invoiceDate(invoice.getInvoiceDate())
                .dueDate(invoice.getDueDate())
                .receptionDate(invoice.getReceptionDate())
                .savedDate(invoice.getSavedDate())

                .invoiceType(invoice.getInvoiceType())
                .invoiceNumber(invoice.getInvoiceNumber())
                .packingListNumber(invoice.getPackingListNumber())

                .invoiceItems(invoice.getInvoiceItems().stream()
                        .map(this::toInvoiceItemResponse).toList())

                .fixedAsset(invoice.isFixedAsset())
                .observations(invoice.getObservations())
                .subtotal1(invoice.getSubtotal1())
                .discount(invoice.getDiscount())
                .interest(invoice.getInterest())
                .subtotal2(invoice.getSubtotal2())
                .netValue0(invoice.getNetValue0())
                .netValue105(invoice.getNetValue105())
                .netValue21(invoice.getNetValue21())
                .netValue27(invoice.getNetValue27())
                .vat105(invoice.getVat105())
                .vat21(invoice.getVat21())
                .vat27(invoice.getVat27())

                .internalTax(invoice.getInternalTax())

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
}
