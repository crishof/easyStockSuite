package com.crishof.supplierinvoicesv.dto;

import com.crishof.supplierinvoicesv.model.InvoiceItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceUpdateRequest {
    private UUID branchId;
    private UUID locationId;
    private List<InvoiceItem> invoiceItems;
}
