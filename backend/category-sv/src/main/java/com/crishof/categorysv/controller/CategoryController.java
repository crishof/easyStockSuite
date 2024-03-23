package com.crishof.categorysv.controller;

import com.crishof.categorysv.apiCient.ImageAPIClient;
import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.exception.CategoryNotFoundException;
import com.crishof.categorysv.model.Category;
import com.crishof.categorysv.repository.CategoryRepository;
import com.crishof.categorysv.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    ImageAPIClient imageAPIClient;


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
        return categoryService.update(id, name);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.ok("Category successfully deleted");
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    @PutMapping("/updateImage/{id}")
    public ResponseEntity<Category> updateImage(@PathVariable("id") UUID uuid, @RequestParam("file") MultipartFile file) {

        try {
            byte[] fileBytes = file.getBytes();
            String mime = file.getContentType();
            String name = file.getOriginalFilename();

            ResponseEntity<String> responseEntity = imageAPIClient.saveImage(fileBytes, mime, name, Category.class.getSimpleName());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String imageUrl = responseEntity.getBody();

                Category updatedCategory = categoryService.updateImage(uuid, imageUrl);
                return ResponseEntity.ok(updatedCategory);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}