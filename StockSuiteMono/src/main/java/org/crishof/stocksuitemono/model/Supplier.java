package org.crishof.stocksuitemono.model;

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
}
