package com.crishof.categorysv.controller;

import com.crishof.categorysv.model.Category;
import com.crishof.categorysv.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/save")
    public String saveCategory(@RequestBody Category category) {
        categoryService.save(category);
        return "Category created successfully";
    }

    @GetMapping("/getById/{id}")
    public Category getById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/getAll")
    public List<Category> getAllCategories() {
        return categoryService.finAll();
    }

    @PutMapping("/edit/{id}")
    public Category editCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        categoryService.update(id, category);
        return categoryService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "Category successfully deleted";
    }
}
