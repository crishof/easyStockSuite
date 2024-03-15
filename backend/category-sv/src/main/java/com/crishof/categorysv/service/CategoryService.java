package com.crishof.categorysv.service;

import com.crishof.categorysv.model.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);

    List<Category> finAll();

    Category findById(Long id);

    void update(Long id, Category category);

    void deleteById(Long id);


}
