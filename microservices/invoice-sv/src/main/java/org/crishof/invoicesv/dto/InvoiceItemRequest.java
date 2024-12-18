package org.crishof.invoicesv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItemRequest {

    private UUID id;
    private Integer quantity;
    private Double price;
    private Double taxRate;
    private Double discountRate;

}
