package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.CategoryRequest;
import org.crishof.stocksuitemono.dto.CategoryResponse;
import org.crishof.stocksuitemono.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAll();

    Category getById(UUID id);

    Category getByName(String name);

    CategoryResponse save(CategoryRequest categoryRequest);

    CategoryResponse update(UUID id, CategoryRequest categoryRequest);

    void deleteById(UUID id);
}
