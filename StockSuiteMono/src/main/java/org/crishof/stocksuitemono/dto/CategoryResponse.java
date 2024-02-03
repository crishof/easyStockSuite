package org.crishof.stocksuitemono.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crishof.stocksuitemono.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
