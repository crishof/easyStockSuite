package com.crishof.brandsv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_brand")
public class Brand {

    @Id
    @Column(name = "brand_id")
    @GeneratedValue
    private UUID id;
    private String name;
    private String imageUrl;

}