package com.crishof.supplier.dto;

import com.crishof.supplier.model.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {

    private UUID id;
    private String name;
    private String taxId;
    private String legalName;

    public SupplierResponse(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.taxId = supplier.getTaxId();
        this.legalName = supplier.getLegalName();
    }
}
