package org.crishof.invoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.invoicesv.model.InvoiceItem;

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
