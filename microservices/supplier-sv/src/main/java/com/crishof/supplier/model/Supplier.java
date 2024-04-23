package com.crishof.supplier.model;

import com.crishof.supplier.dto.SupplierRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_supplier")
public class Supplier {

    @Id
    @Column(name = "supplier_id")
    @GeneratedValue
    private UUID id;
    private String name;
    private String taxId;
    private String legalName;

    public Supplier(SupplierRequest supplierRequest) {
        this.name = supplierRequest.getName();
        this.taxId = supplierRequest.getTaxId();
        this.legalName = supplierRequest.getLegalName();
    }
}

