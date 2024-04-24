package com.crishof.supplierinvoicesv.dto;

import com.crishof.supplierinvoicesv.model.InvoiceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    List<InvoiceItem> productRequests;
    private UUID id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate receivedDate;
    private Double discount;
    private Double total;
    private UUID supplierId;
}
