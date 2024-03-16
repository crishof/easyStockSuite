package com.crishof.categorysv.controller;

import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.repository.CategoryRepository;
import com.crishof.categorysv.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    final
    CategoryService categoryService;
    final
    CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/getAll")
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/getById/{id}")
    public CategoryResponse getById(@PathVariable("id") UUID id) {
        return categoryService.getById(id);
    }

    @PostMapping("/save")
    public CategoryResponse save(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    @PutMapping("/update/{id}")
    public CategoryResponse update(@PathVariable("id") UUID id, @RequestParam(required = false) String name) {

        System.out.println("Controller name = " + name);
        return categoryService.update(id, name);
    }
//
//    @PutMapping("/updateLogo/{id}")
//    public Category updateLogo(@PathVariable("id") UUID uuid, @RequestParam(required = false) MultipartFile logo) {

//        return categoryService.updateLogo(uuid, logo);

//    }

//    @DeleteMapping("/delete/{id}")
//    public String delete(@PathVariable("id") UUID id) {
//        categoryService.deleteById(id);
//        return "Category successfully deleted";
//    }
}
