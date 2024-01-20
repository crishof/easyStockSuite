package com.crishof.brandsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_brand")
public class Brand {

    @Id
    @Column(name = "id_brand")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
