package com.crishof.supplierinvoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {

    private UUID supplierId;

    private String location;

    private UUID branchId;
    private UUID locationId;

    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate receptionDate;
    private LocalDate savedDate;

    private String invoiceType;
    private String invoicePrefix;
    private String invoiceNumber;
    private String packingListPrefix;
    private String packingListNumber;

    private List<InvoiceItemRequest> invoiceItemsRequest;

    private boolean saveStocks;
    private boolean taxSave;
    private boolean fixedAsset;
    private boolean askForPriceUpdate;

    private String observations;

    private double subtotal1;
    private Double discount;
    private double interest;
    private double subtotal2;

    private double netoIva21;
    private double iva21;
    private double netoIva105;
    private double iva105;
    private double netoIva27;
    private double iva27;
    private double netoIva0;

    private double impuestoInterno;

    private double withholdingIva;
    private double withholdingSuss;
    private double withholdingIibb;
    private double withholdingIncome;
    private double stateTax;
    private double localTax;

    private double rounding;

    private double totalPrice;

}