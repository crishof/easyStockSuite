package com.crishof.categorysv.model;

import com.crishof.categorysv.dto.CategoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "image_id")
    private UUID imageId;
    @Embedded
    private Dimension dimension;

    public Category(CategoryRequest categoryRequest) {
        this.name = categoryRequest.getName();
    }
}
