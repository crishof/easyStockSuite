package org.crishof.stocksuitemono.controller;


import org.crishof.stocksuitemono.model.Category;
import org.crishof.stocksuitemono.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/findAll")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Category findById(@PathVariable("id") Long id) {
        return categoryService.findtById(id);
    }

    @PostMapping("/save")
    public String save(@RequestBody Category category) {
        categoryService.save(category);

        return "Category successfully saved";
    }

    @PutMapping("/edit/{id}")
    public Category editCategory(@RequestParam("id") Long id, @RequestBody Category category) {
        categoryService.update(id, category);
        return categoryService.findtById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "Category successfully deleted";
    }
}
