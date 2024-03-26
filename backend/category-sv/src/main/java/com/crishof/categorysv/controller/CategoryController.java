package com.crishof.categorysv.controller;

import com.crishof.categorysv.apiCient.ImageAPIClient;
import com.crishof.categorysv.dto.CategoryRequest;
import com.crishof.categorysv.dto.CategoryResponse;
import com.crishof.categorysv.exception.CategoryNotFoundException;
import com.crishof.categorysv.exception.DuplicateNameException;
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
    public ResponseEntity<?> getById(@PathVariable("id") UUID id) {
        try {
            CategoryResponse category = categoryService.getById(id);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getById(@RequestParam String name) {
        try {
            CategoryResponse category = categoryService.getByName(name);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/getIdByName")
    public ResponseEntity<?> getIdByName(@RequestParam String categoryName) {
        try {
            CategoryResponse categoryResponse = categoryService.getByName(categoryName);
            return ResponseEntity.ok(categoryResponse.getId());
        } catch (CategoryNotFoundException e) {
            CategoryRequest categoryRequest = new CategoryRequest(categoryName);
            CategoryResponse categoryResponse = categoryService.save(categoryRequest);
            return ResponseEntity.ok(categoryResponse.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CategoryRequest categoryRequest) {

        try {
            CategoryResponse categoryResponse = categoryService.save(categoryRequest);
            return ResponseEntity.ok(categoryResponse);
        } catch (DuplicateNameException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable("id") UUID id,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) MultipartFile file) {

        try {
            if (categoryName == null && (file == null || file.isEmpty())) {
                return ResponseEntity.badRequest().build();
            }

            if (file != null && !file.isEmpty()) {

                byte[] fileBytes = file.getBytes();
                String mime = file.getContentType();
                String name = file.getOriginalFilename();
                ResponseEntity<String> responseEntity = imageAPIClient.saveImage(fileBytes, mime, name, Category.class.getSimpleName());

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String imageUrl = responseEntity.getBody();
                    return ResponseEntity.ok().body(categoryService.updateCategory(id, categoryName, imageUrl));
                } else {
                    return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
                }
            } else {
                return ResponseEntity.ok().body(categoryService.updateCategoryName(id, categoryName));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateCategoryName/{id}")
    public CategoryResponse updateCategoryName(@PathVariable("id") UUID id, @RequestParam(required = false) String name) {
        return categoryService.updateCategoryName(id, name);
    }

    @PutMapping("/updateImage/{id}")
    public ResponseEntity<CategoryResponse> updateImage(@PathVariable("id") UUID id, @RequestParam("file") MultipartFile file) {

        try {
            byte[] fileBytes = file.getBytes();
            String mime = file.getContentType();
            String name = file.getOriginalFilename();

            ResponseEntity<String> responseEntity = imageAPIClient.saveImage(fileBytes, mime, name, Category.class.getSimpleName());

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String imageUrl = responseEntity.getBody();

                CategoryResponse updatedCategory = categoryService.updateImage(id, imageUrl);
                return ResponseEntity.ok(updatedCategory);
            } else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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


}