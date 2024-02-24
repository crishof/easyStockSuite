package org.crishof.stocksuitemono.service;

import jakarta.persistence.EntityNotFoundException;
import org.crishof.stocksuitemono.dto.CategoryRequest;
import org.crishof.stocksuitemono.dto.CategoryResponse;
import org.crishof.stocksuitemono.exception.notFound.CategoryNotFoundException;
import org.crishof.stocksuitemono.model.Category;
import org.crishof.stocksuitemono.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(UUID id) {

        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException("Category not found with name: " + name));
    }


    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = new Category(categoryRequest);
        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(UUID id, CategoryRequest categoryRequest) {

        Category category = this.getById(id);

        category.setName(categoryRequest.getName());

        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }
}
