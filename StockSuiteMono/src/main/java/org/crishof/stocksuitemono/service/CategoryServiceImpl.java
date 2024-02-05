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

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {

        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = new Category(categoryRequest);
        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {

        Category category = this.getById(id);

        category.setName(categoryRequest.getName());

        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
