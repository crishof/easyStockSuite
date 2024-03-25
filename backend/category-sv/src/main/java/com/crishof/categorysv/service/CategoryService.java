package com.crishof.categorysv.service;

import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryResponse> getAll();

    CategoryResponse getById(UUID id);

    CategoryResponse getByName(String name);

    CategoryResponse save(CategoryRequest categoryRequest);

    CategoryResponse updateCategoryName(UUID uuid, String name);

    CategoryResponse updateCategory(UUID id, String name, String imageUrl);

    CategoryResponse updateImage(UUID uuid, String imageUrl);

    void deleteById(UUID id);

}
