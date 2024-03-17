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

import java.io.IOException;
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

    @PostMapping("/updateLogo/{id}")
    public Category updateLogo(@PathVariable("id") UUID uuid, @RequestBody MultipartFile file) throws IOException {

        byte[] fileBytes = file.getBytes();
        String mime = file.getContentType();
        String name = file.getOriginalFilename();

        UUID imageId = imageAPIClient.saveImage(fileBytes, mime, name);

        return categoryService.updateLogo(uuid, imageId);

    }
    @PostMapping("/imageTest")
    public ResponseEntity<String> test(@RequestParam String text, @RequestBody MultipartFile file) throws IOException {

        byte[] fileBytes = file.getBytes();
        String mime = file.getContentType();
        String name = file.getOriginalFilename();

        // Llama al método del servicio de imágenes para procesar el archivo
        imageAPIClient.test(fileBytes, mime, name);

        // Retorna una respuesta exitosa
        return ResponseEntity.ok("Archivo enviado correctamente");
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.ok("Category successfully deleted desde back");
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());

        }
    }

    @GetMapping("/getAll")
    public List<CategoryResponse> getAll() {

        System.out.println("GET ALL CATEGORIES");
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
}