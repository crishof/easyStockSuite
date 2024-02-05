package org.crishof.stocksuitemono.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.dto.CategoryRequest;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tbl_category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;

    public Category(CategoryRequest categoryRequest) {
        this.name = categoryRequest.getName();
    }

    public Category(String name) {
        this.name = name;
    }
}
