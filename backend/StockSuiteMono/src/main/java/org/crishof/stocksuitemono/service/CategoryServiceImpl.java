package org.crishof.stocksuitemono.service;

import org.crishof.stocksuitemono.dto.CategoryRequest;
import org.crishof.stocksuitemono.dto.CategoryResponse;
import org.crishof.stocksuitemono.exception.notFound.BrandNotFoundException;
import org.crishof.stocksuitemono.exception.notFound.CategoryNotFoundException;
import org.crishof.stocksuitemono.model.Category;
import org.crishof.stocksuitemono.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageService imageService;

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
    public Category update(UUID id, String name) {

        System.out.println("name en service" + name);
        Category category = this.getById(id);
        if (category != null) {
            if (Objects.equals(name, "")) {
                throw new IllegalArgumentException("Category name cannot be empty");
            }
            category.setName(name);
            return categoryRepository.save(category);
        } else {
            throw new CategoryNotFoundException(id);
        }
    }

    @Override
    public Category updateLogo(UUID id, MultipartFile logo) {

        Category category = this.getById(id);
        if (category != null) {
            if (category.getImage() != null) {
                category.setImage(imageService.update(category.getImage().getId(), logo));
            } else {
                category.setImage(imageService.save(logo));
            }
        } else {
            throw new BrandNotFoundException(id);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }
}
