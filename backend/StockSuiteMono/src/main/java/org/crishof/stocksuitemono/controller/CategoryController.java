package org.crishof.stocksuitemono.controller;


import org.crishof.stocksuitemono.model.Brand;
import org.crishof.stocksuitemono.model.Category;
import org.crishof.stocksuitemono.repository.CategoryRepository;
import org.crishof.stocksuitemono.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
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
    public Category getById(@PathVariable("id") UUID id) {
        return categoryService.getById(id);
    }

    @PostMapping("/save")
    public Category save(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable("id") UUID id, @RequestParam(required = false) String name) {

        System.out.println("name = " + name);
        return categoryService.update(id, name);
    }

    @PutMapping("/updateLogo/{id}")
    public Category updateLogo(@PathVariable("id") UUID uuid, @RequestParam(required = false) MultipartFile logo) {

        return categoryService.updateLogo(uuid, logo);

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id) {
        categoryService.deleteById(id);
        return "Category successfully deleted";
    }
}
