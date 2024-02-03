package org.crishof.stocksuitemono.controller;


import org.crishof.stocksuitemono.model.Category;
import org.crishof.stocksuitemono.repository.CategoryRepository;
import org.crishof.stocksuitemono.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/findAll")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Category findById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @PostMapping("/save")
    public Category save(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/edit/{id}")
    public Category editCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "Category successfully deleted";
    }
}
