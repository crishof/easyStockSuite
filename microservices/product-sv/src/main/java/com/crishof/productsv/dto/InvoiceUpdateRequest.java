package com.crishof.productsv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceUpdateRequest {
    private UUID branchId;
    private UUID locationId;
    private List<SupplierInvoiceItem> invoiceItems;
}
