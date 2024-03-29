package com.crishof.categorysv.service;

import com.crishof.categorysv.apiCient.ImageAPIClient;
import com.crishof.categorysv.apiCient.ProductApiClient;
import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.exception.CategoryDeleteException;
import com.crishof.categorysv.exception.CategoryNotFoundException;
import com.crishof.categorysv.exception.DuplicateNameException;
import com.crishof.categorysv.model.Category;
import com.crishof.categorysv.modelMapper.CategoryMapper;
import com.crishof.categorysv.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageAPIClient imageAPIClient;
    @Autowired
    ProductApiClient productApiClient;

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(UUID id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(id));
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse getByName(String name) {

        Category category = categoryRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new CategoryNotFoundException("Category with name " + name + " not found"));
        return CategoryMapper.toCategoryResponse(category);
    }


    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {

        Category category = new Category(categoryRequest);

        if (categoryRepository.findByNameIgnoreCase(categoryRequest.getName()).isPresent()) {
            throw new DuplicateNameException("Category with name " + categoryRequest.getName() + " already exist");
        }
        if (Objects.equals(category.getName(), "")) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategoryName(UUID id, String name) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        category.setName(name);
        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(UUID id, String name, String imageUrl) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        category.setName(name);
        category.setImageUrl(imageUrl);

        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateImage(UUID uuid, String imageUrl) {

        Category category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFoundException(uuid));

        if (category.getImageUrl() != null) {
            imageAPIClient.deleteImageByUrl(category.getImageUrl(), Category.class.getSimpleName());
        }
        category.setImageUrl(imageUrl);

        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteById(UUID id) {
        ResponseEntity<String> response = productApiClient.removeCategory(id);

        if (response.getStatusCode().is2xxSuccessful()) {
            deleteCategoryImage(id);
            deleteCategoryFromDatabase(id);
        } else {
            throw new CategoryDeleteException("Failed to remove category from associated products");
        }
    }

    @Override
    public void deleteCategoryFromDatabase(UUID id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteCategoryImage(UUID id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        if (category.getImageUrl() != null) {
            imageAPIClient.deleteImageByUrl(category.getImageUrl(), Category.class.getSimpleName());
        }
    }
}
