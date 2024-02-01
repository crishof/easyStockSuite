package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findtById(Long id);

    void save(Category category);

    Category update(Long id, Category category);

    void deleteById(Long id);
}
