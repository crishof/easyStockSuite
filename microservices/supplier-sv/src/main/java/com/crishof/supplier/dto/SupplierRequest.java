package com.crishof.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierRequest {

    private UUID id;
    private String name;
    private String taxId;
    private String legalName;

    public SupplierRequest(String supplierName) {
        this.name = supplierName;
    }
}
