package com.crishof.categorysv.service;

import com.crishof.categorysv.apiCient.ImageAPIClient;
import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.exception.CategoryNotFoundException;
import com.crishof.categorysv.model.Category;
import com.crishof.categorysv.modelMapper.CategoryMapper;
import com.crishof.categorysv.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageAPIClient imageAPIClient;

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(UUID id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        return CategoryMapper.toCategoryResponse(category);
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
    public CategoryResponse update(UUID id, String name) {
        System.out.println("name en service" + name);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        category.setName(name);

        return new CategoryResponse(categoryRepository.save(category));
    }

    @Override
    public Category updateImage(UUID uuid, String imageUrl) {

        Category category = categoryRepository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + uuid));

        if (category != null) {

//            if (category.getImageUrl() != null) {
//                imageAPIClient.deleteImage(category.getImageId());
//            }
            category.setImageUrl(imageUrl);
        } else {
            throw new CategoryNotFoundException(uuid);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateLogo(UUID id, UUID imageId) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        if (category != null) {

            if (category.getImageId() != null) {
                imageAPIClient.deleteImage(category.getImageId());
            }
            category.setImageId(imageId);
        } else {
            throw new CategoryNotFoundException(id);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(UUID id) {

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        categoryRepository.deleteById(id);

        if (category.getImageId() != null) {
            imageAPIClient.deleteImage(category.getImageId());
        }
    }
}
