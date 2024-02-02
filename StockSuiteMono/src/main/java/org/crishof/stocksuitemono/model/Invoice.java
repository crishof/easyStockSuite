package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long id;
    private Long invoiceNumber;
    private LocalDate issueDate;
    private LocalDate receptionDate;
    private LocalDate dueDate;
    private Long SupplierId;
    @OneToMany
    @JoinColumn(name = "invoice_id")
    private List<Product> productList;

}
