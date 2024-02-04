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

    @GetMapping("/getAll")
    public List<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/getById/{id}")
    public Category getById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    @PostMapping("/save")
    public Category save(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable("id") Long id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "Category successfully deleted";
    }
}
