package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.CategoryRequest;
import org.crishof.stocksuitemono.dto.CategoryResponse;
import org.crishof.stocksuitemono.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getById(Long id);

    Category getByName(String name);

    CategoryResponse save(CategoryRequest categoryRequest);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    void deleteById(Long id);
}
