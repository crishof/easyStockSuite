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

    private double netValue21;
    private double vat21;
    private double netValue105;
    private double vat105;
    private double netValue27;
    private double vat27;
    private double netValue0;

    private double internalTax;

    private double withholdingVat;
    private double withholdingSuss;
    private double withholdingGrossReceiptsTax;
    private double withholdingIncome;
    private double stateTax;
    private double localTax;

    private double rounding;

    private double totalPrice;

    @Override
    public String toString() {
        return "InvoiceRequest {\n" +
                "  supplierId=" + supplierId + ",\n" +
                "  location='" + location + "',\n" +
                "  branchId=" + branchId + ",\n" +
                "  locationId=" + locationId + ",\n" +
                "  invoiceDate=" + invoiceDate + ",\n" +
                "  dueDate=" + dueDate + ",\n" +
                "  receptionDate=" + receptionDate + ",\n" +
                "  savedDate=" + savedDate + ",\n" +
                "  invoiceType='" + invoiceType + "',\n" +
                "  invoicePrefix='" + invoicePrefix + "',\n" +
                "  invoiceNumber='" + invoiceNumber + "',\n" +
                "  packingListPrefix='" + packingListPrefix + "',\n" +
                "  packingListNumber='" + packingListNumber + "',\n" +
                "  invoiceItemsRequest=" + invoiceItemsRequest + ",\n" +
                "  saveStocks=" + saveStocks + ",\n" +
                "  taxSave=" + taxSave + ",\n" +
                "  fixedAsset=" + fixedAsset + ",\n" +
                "  askForPriceUpdate=" + askForPriceUpdate + ",\n" +
                "  observations='" + observations + "',\n" +
                "  subtotal1=" + subtotal1 + ",\n" +
                "  discount=" + discount + ",\n" +
                "  interest=" + interest + ",\n" +
                "  subtotal2=" + subtotal2 + ",\n" +
                "  netValue21=" + netValue21 + ",\n" +
                "  vat21=" + vat21 + ",\n" +
                "  netValue105=" + netValue105 + ",\n" +
                "  vat105=" + vat105 + ",\n" +
                "  netValue27=" + netValue27 + ",\n" +
                "  vat27=" + vat27 + ",\n" +
                "  netValue0=" + netValue0 + ",\n" +
                "  internalTax=" + internalTax + ",\n" +
                "  withholdingVat=" + withholdingVat + ",\n" +
                "  withholdingSuss=" + withholdingSuss + ",\n" +
                "  withholdingGrossReceiptsTax=" + withholdingGrossReceiptsTax + ",\n" +
                "  withholdingIncome=" + withholdingIncome + ",\n" +
                "  stateTax=" + stateTax + ",\n" +
                "  localTax=" + localTax + ",\n" +
                "  rounding=" + rounding + ",\n" +
                "  totalPrice=" + totalPrice + "\n" +
                '}';
    }
}