package org.crishof.stocksuitemono.service;

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
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findtById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }


    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {

        Category category1 = this.findtById(id);

        category1.setName(category.getName());

        return categoryRepository.save(category1);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
