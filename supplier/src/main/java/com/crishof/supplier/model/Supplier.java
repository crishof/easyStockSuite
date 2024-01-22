package com.crishof.supplier.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_supplier")
public class Supplier {

    @Id
    @Column(name = "id_supplier")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String companyName;
    private String taxIN;
}
